package apony.lol.LooserQAnalyse.service.implementations;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.model.enumeration.Platform;
import apony.lol.LooserQAnalyse.service.interfaces.IPlayerService;
import apony.lol.LooserQAnalyse.service.interfaces.IRequestService;

@Service
public class IPlayerServiceImpl implements IPlayerService {

    @Autowired
    IRequestService requestService;

    private static final String SUMMONER_BY_NAME_ENDPOINT = "/lol/summoner/v4/summoners/by-name/";

    Logger logger = LoggerFactory.getLogger(IPlayerServiceImpl.class);

    @Override
    public String getPlayerPuuidByNameAndPlatform(String name, Platform platform) throws NotResultException {
        HttpClient httpClient = requestService.createHttpClient();
        StringBuilder strbUri = requestService.createRequestUri(platform.getPath(), SUMMONER_BY_NAME_ENDPOINT);
        strbUri.append(name);
        String res = requestService.sendGetRequest(strbUri.toString(), httpClient);
        try {
            JSONObject resJson = new JSONObject(res);
            return resJson.getString("puuid");
        } catch (JSONException e) {
            logger.error("Erreur lors de la récupération du puuid", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error, invalid response by RIOT API");
        }

    }

}
