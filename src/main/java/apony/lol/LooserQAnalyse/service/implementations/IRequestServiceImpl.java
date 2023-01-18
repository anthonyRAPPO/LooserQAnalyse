package apony.lol.LooserQAnalyse.service.implementations;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.service.interfaces.IRefService;
import apony.lol.LooserQAnalyse.service.interfaces.IRequestService;

@Service
public class IRequestServiceImpl implements IRequestService {

    @Autowired
    IRefService refService;

    private static final String RIOT_TOKEN_NAME = "X-Riot-Token";

    @Value("${riot.token}")
    private String riotToken;

    Logger logger = LoggerFactory.getLogger(IRequestServiceImpl.class);

    @Override
    public HttpClient createHttpClient() {
        try {
            return HttpClients
                    .custom()
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                            TrustAllStrategy.INSTANCE).build())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            logger.error("Erreur lors de la création du client HTTP", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public StringBuilder createRequestUri(String baseUrl, String summonerEndmpoint) {
        StringBuilder strb = new StringBuilder("https://");
        strb.append(baseUrl);
        strb.append(summonerEndmpoint);
        return strb;
    }

    @Override
    public String sendGetRequest(String req, HttpClient httpClient) throws NotResultException {
        HttpGet httpGet = new HttpGet(req);
        httpGet.setHeader(RIOT_TOKEN_NAME, riotToken);
        logger.info(String.format("Envoie de la requete : %s ", req));
        if (requestLimitOk()) {
            try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet)) {
                checkResponseCode(response);
                HttpEntity entity = response.getEntity();
                if (Objects.isNull(entity)) {
                    logger.error(String.format("Erreur, réponse NULL pour la requete %s", req));
                    EntityUtils.consume(entity);
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Error, invalid response by RIOT API");
                }
                String retSrc = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
                return retSrc;
            } catch (IOException e) {
                logger.error("Erreur lors de l'envoie de la requête HTTP GET", e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error,can't request RIOT API");
            }
        } else {
            logger.error("Trop de requêtes ont été envoyées à l'API de riot");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "To much request send to RIOT API,please try later");
        }
    }

    private boolean requestLimitOk() {
        int i = 0;
        while (!refService.canRequest() && i < 200) {
            try {
                TimeUnit.SECONDS.sleep(1);
                i++;
            } catch (InterruptedException e) {
                logger.error("methode sleep interrompue", e);
            }
        }
        return !(i == 10);
    }

    private void checkResponseCode(CloseableHttpResponse response) throws NotResultException {
        int code = response.getStatusLine().getStatusCode();
        switch (code) {
            case 400:
                logger.error("Requete envoyée invalide");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during RIOT API request");
            case 401:
                logger.error("La requete ne possède pas le necessaire pour l'authentification (API KEY)");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during RIOT API request");
            case 403:
                logger.error(
                        "La requete ne possède pas le necessaire pour l'authentification (API KEY,blacklist,path non supporté)");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during RIOT API request");
            case 404:
                throw new NotResultException();
            case 405:
                logger.error(
                        "La requete n'est pas autorisée");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during RIOT API request");
            case 415:
                logger.error("Erreur de format dans la requete (peut être Content-type)");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during RIOT API request");
            case 429:
                logger.error("Nombre limite de requete effectué");
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Error during RIOT API request");
            case 500:
                logger.error("Erreur lors du traitement de la réponse par l'API de RIOT");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during RIOT API request");
            case 502:
                logger.error("Erreur lors du traitement de la réponse par l'API de RIOT, bad gateway");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during RIOT API request");
            case 503:
                logger.error("API de riot indisponible");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during RIOT API request");
            case 504:
                logger.error("API de riot timeout");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during RIOT API request");
        }
    }

}
