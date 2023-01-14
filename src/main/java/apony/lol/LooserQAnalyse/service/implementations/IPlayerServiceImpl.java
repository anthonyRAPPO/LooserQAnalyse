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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apony.lol.LooserQAnalyse.model.enumeration.Regions;
import apony.lol.LooserQAnalyse.service.interfaces.IPlayerService;
import apony.lol.LooserQAnalyse.service.interfaces.IRequestService;

@Service
public class IPlayerServiceImpl implements IPlayerService {

    @Autowired
    IRequestService requestService;

    private static final String SUMMONER_ENDMPOINT = "/lol/summoner/v4/summoners/by-name/";

    Logger logger = LoggerFactory.getLogger(IPlayerServiceImpl.class);

    @Override
    public String getPlayerPuuidByNameAndRegion(String name, Regions region) {
        HttpClient httpClient = requestService.createHttpClient();
        StringBuilder strbUri = requestService.createRequestUri(region, SUMMONER_ENDMPOINT);
        strbUri.append(name);
        JSONObject resJson = requestService.sendGetRequest(strbUri.toString(), httpClient);
        try {
            return resJson.getString("puuid");
        } catch (JSONException e) {
            logger.error("Erreur lors de la récupération du puuid", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error, invalid response by RIOT API");
        }

    }

}
