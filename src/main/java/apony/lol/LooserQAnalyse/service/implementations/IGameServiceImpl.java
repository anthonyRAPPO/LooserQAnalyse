package apony.lol.LooserQAnalyse.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.model.enumeration.Region;
import apony.lol.LooserQAnalyse.service.interfaces.IGameService;
import apony.lol.LooserQAnalyse.service.interfaces.IRequestService;
import apony.lol.LooserQAnalyse.service.interfaces.IUtilService;

@Service
public class IGameServiceImpl implements IGameService {

    private static final String MATCH_BY_PUUID_ENDPOINT = "/lol/match/v5/matches/by-puuid/";

    @Autowired
    IRequestService requestService;

    @Autowired
    IUtilService utilService;

    Logger logger = LoggerFactory.getLogger(IGameServiceImpl.class);

    @Override
    public List<String> getHistoryByPuuidAndNumber(String userPuuid, int count) throws NotResultException {
        HttpClient httpClient = requestService.createHttpClient();
        StringBuilder strbUri = requestService.createRequestUri(Region.EUROPE.getPath(),
                String.format("%s%s/ids?count=%d", MATCH_BY_PUUID_ENDPOINT, userPuuid, count));
        String res = requestService.sendGetRequest(strbUri.toString(), httpClient);
        return utilService.convertResponseToListString(res);
    }

}
