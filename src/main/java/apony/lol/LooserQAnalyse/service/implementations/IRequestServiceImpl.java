package apony.lol.LooserQAnalyse.service.implementations;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import apony.lol.LooserQAnalyse.model.enumeration.Regions;
import apony.lol.LooserQAnalyse.service.interfaces.IRequestService;

@Service
public class IRequestServiceImpl implements IRequestService {

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
    public StringBuilder createRequestUri(Regions region, String summonerEndmpoint) {
        StringBuilder strb = new StringBuilder("https://");
        strb.append(region.getPath());
        strb.append(summonerEndmpoint);
        return strb;
    }

    @Override
    public JSONObject sendGetRequest(String req, HttpClient httpClient) {
        HttpGet httpGet = new HttpGet(req);
        httpGet.setHeader(RIOT_TOKEN_NAME, riotToken);
        try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            if (Objects.isNull(entity)) {
                logger.error(String.format("Erreur, réponse NULL pour la requete %s", req));
                EntityUtils.consume(entity);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error, invalid response by RIOT API");
            }
            String retSrc = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return new JSONObject(retSrc);
        } catch (IOException e) {
            logger.error("Erreur lors de l'envoie de la requête HTTP GET", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error,can't request RIOT API");
        }
    }

}
