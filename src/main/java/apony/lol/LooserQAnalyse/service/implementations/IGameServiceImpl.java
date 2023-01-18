package apony.lol.LooserQAnalyse.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.model.Game;
import apony.lol.LooserQAnalyse.model.Participant;
import apony.lol.LooserQAnalyse.model.Recap;
import apony.lol.LooserQAnalyse.model.enumeration.Queue;
import apony.lol.LooserQAnalyse.model.enumeration.Region;
import apony.lol.LooserQAnalyse.service.interfaces.IGameService;
import apony.lol.LooserQAnalyse.service.interfaces.IRequestService;
import apony.lol.LooserQAnalyse.service.interfaces.IUtilService;

@Service
public class IGameServiceImpl implements IGameService {

    private static final String MATCH_BY_PUUID_ENDPOINT = "/lol/match/v5/matches/by-puuid/";
    private static final String MATCH_BY_ID_ENDPOINT = "/lol/match/v5/matches/";

    @Autowired
    IRequestService requestService;

    @Autowired
    IUtilService utilService;

    Logger logger = LoggerFactory.getLogger(IGameServiceImpl.class);

    @Override
    public List<String> getHistoryByPuuidQueueDateNumber(String userPuuid, Queue queue, LocalDateTime dateDebut,
            LocalDateTime dateFin, int count) throws NotResultException {

        HttpClient httpClient = requestService.createHttpClient();
        StringBuilder strbUri = requestService.createRequestUri(Region.EUROPE.getPath(),
                String.format("%s%s/ids?count=%d&startTime=%d&endTime=%d&queue=%d", MATCH_BY_PUUID_ENDPOINT, userPuuid,
                        count, dateDebut.toEpochSecond(ZoneOffset.UTC), dateFin.toEpochSecond(ZoneOffset.UTC),
                        queue.getId()));
        String res = requestService.sendGetRequest(strbUri.toString(), httpClient);
        return utilService.convertResponseToListString(res);
    }

    @Override
    public Game getGameById(String gameid) throws NotResultException {
        HttpClient httpClient = requestService.createHttpClient();
        StringBuilder strbUri = requestService.createRequestUri(Region.EUROPE.getPath(), MATCH_BY_ID_ENDPOINT);
        strbUri.append(gameid);
        String res = requestService.sendGetRequest(strbUri.toString(), httpClient);
        try {
            JSONObject resJson = new JSONObject(res);
            return convertResJsonToGame(resJson, gameid);
        } catch (JSONException e) {
            logger.error("Erreur lors de la récupération de la game par id", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error, invalid response by RIOT API");
        }
    }

    private Game convertResJsonToGame(JSONObject resJson, String gameid) {
        JSONObject info = resJson.getJSONObject("info");
        List<Participant> participantLst = new ArrayList<>();
        JSONArray participantsJsonLst = info.getJSONArray("participants");
        for (int i = 0; i < participantsJsonLst.length(); i++) {
            Participant participant = new Participant();
            JSONObject participantJson = participantsJsonLst.getJSONObject(i);
            participant.setPuuid(participantJson.getString("puuid"));
            participant.setTeamId(participantJson.getInt("teamId"));
            participant.setWin(participantJson.getBoolean("win"));
            participantLst.add(participant);
        }
        String gameCreationStr = String.valueOf(info.getLong("gameCreation"));
        return new Game(gameid, participantLst,
                Long.parseLong(gameCreationStr.substring(0, gameCreationStr.length() - 3)));

    }

    @Override
    public List<Game> getGameListByGameIdList(List<String> userGameIds) throws NotResultException {
        List<Game> lstgame = new ArrayList<>();
        for (String gameId : userGameIds) {
            if (!gameId.isEmpty()) {
                lstgame.add(getGameById(gameId));
            }

        }
        return lstgame;
    }

    @Override
    public void fillRecapByLstGame(List<Game> gameUserLst, String userPuuid, Recap recap, boolean ally) {
        for (Game game : gameUserLst) {
            Participant p = getParticipantFromGameWithPuuid(userPuuid, game);
            if (ally) {
                if (p.isWin()) {
                    recap.incrementNbVictoryAlly();
                } else {
                    recap.incrementNbLooseAlly();
                }
                recap.incrementTotalGameAlly();
            } else {
                if (p.isWin()) {
                    recap.incrementNbVictoryEnnemy();
                } else {
                    recap.incrementNbLooseEnnemy();
                }
                recap.incrementTotalGameEnnemy();
            }
        }
    }

    @Override
    public int getTeamIdFromPuuid(Game selectedGame, String userPuuid) {
        return getParticipantFromGameWithPuuid(userPuuid, selectedGame).getTeamId();
    }

    private Participant getParticipantFromGameWithPuuid(String userPuuid, Game game) {
        List<Participant> lstParticipantUser = game.getLstParticipants().stream()
                .filter(p -> p.getPuuid().equals(userPuuid)).collect(Collectors.toList());
        if (lstParticipantUser.size() != 1) {
            logger.error("Une game possede 0 ou plus de 1 fois le user");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return lstParticipantUser.get(0);
    }

}
