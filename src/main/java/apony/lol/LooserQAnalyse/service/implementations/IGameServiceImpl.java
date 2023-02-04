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
    public List<String> getHistoryByPuuidQueueDateNumber(String userPuuid, Queue queue, long dateDebutEpochSecond,
            long dateFinEpochSecond, int count, Region region) throws NotResultException {

        HttpClient httpClient = requestService.createHttpClient();
        StringBuilder strbUri = requestService.createRequestUri(region.getPath(),
                String.format("%s%s/ids?count=%d&startTime=%d&endTime=%d&queue=%d", MATCH_BY_PUUID_ENDPOINT, userPuuid,
                        count, dateDebutEpochSecond, dateFinEpochSecond,
                        queue.getId()));
        String res = requestService.sendGetRequest(strbUri.toString(), httpClient);
        return utilService.convertResponseToListString(res);
    }

    @Override
    public Game getGameById(String gameid, String userPuuid, Region region) throws NotResultException {
        HttpClient httpClient = requestService.createHttpClient();
        StringBuilder strbUri = requestService.createRequestUri(region.getPath(), MATCH_BY_ID_ENDPOINT);
        strbUri.append(gameid);
        String res = requestService.sendGetRequest(strbUri.toString(), httpClient);
        try {
            JSONObject resJson = new JSONObject(res);
            return convertResJsonToGame(resJson, gameid, userPuuid);
        } catch (JSONException e) {
            logger.error("Erreur lors de la récupération de la game par id", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error, invalid response by RIOT API");
        }
    }

    private Game convertResJsonToGame(JSONObject resJson, String gameid, String userPuuid) {
        JSONObject info = resJson.getJSONObject("info");
        List<Participant> participantLst = new ArrayList<>();
        JSONArray participantsJsonLst = info.getJSONArray("participants");
        int teamId = -1;
        boolean win = false;
        String champPlayed = "";
        int kills = -1;
        int deaths = -1;
        int assists = -1;
        for (int i = 0; i < participantsJsonLst.length(); i++) {
            Participant participant = new Participant();
            JSONObject participantJson = participantsJsonLst.getJSONObject(i);
            participant.setPuuid(participantJson.getString("puuid"));
            participant.setTeamId(participantJson.getInt("teamId"));
            participant.setWin(participantJson.getBoolean("win"));
            participant.setSummonerName(participantJson.getString("summonerName"));
            participant.setChampionPlayed(participantJson.getString("championName"));
            participant.setTeamPosition(participantJson.getString("teamPosition"));
            if (participant.getPuuid().equals(userPuuid)) {
                teamId = participantJson.getInt("teamId");
                win = participant.isWin();
                champPlayed = participant.getChampionPlayed();
                kills = participantJson.getInt("kills");
                deaths = participantJson.getInt("deaths");
                assists = participantJson.getInt("assists");
            }
            participantLst.add(participant);
        }
        String gameCreationStr = String.valueOf(info.getLong("gameCreation"));
        if (teamId == -1 || champPlayed.isEmpty() || kills == -1 || deaths == -1 || assists == -1) {
            logger.error("Erreur lors de la recuperation de l'id team");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error, invalid response by RIOT API");
        }
        return new Game(gameid, participantLst,
                Long.parseLong(gameCreationStr.substring(0, gameCreationStr.length() - 3)), teamId, win, champPlayed,
                userPuuid, assists, deaths, kills);
    }

    @Override
    public List<Game> getGameListByGameIdList(List<String> userGameIds, String userPuuid, Region region) {
        List<Game> lstgame = new ArrayList<>();
        for (String gameId : userGameIds) {
            if (!gameId.isEmpty()) {
                try {
                    lstgame.add(getGameById(gameId, userPuuid, region));
                } catch (NotResultException e) {
                    logger.error(String.format("La game avec l'id = %s n'a pas été trouvée", gameId), e);
                }
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

    @Override
    public void fillParticipantLstForGame(List<Participant> result, Game game, Queue queue, int nbGame, Region region) {
        LocalDateTime dateCreationGame = LocalDateTime.ofEpochSecond(game.getTimeStampCreation(), 0,
                ZoneOffset.UTC);
        LocalDateTime dateLimiteRecherche = dateCreationGame.minusDays(100);
        // Pour la game passée en parametre on recupere l'ensemble des participants
        for (Participant participant : game.getLstParticipants()) {
            try {
                // on determine si le participant est un allié
                participant.setAlly(participant.getTeamId() == game.getAllyTeam());
                // pour chaque participant on recupere les x dernière games
                List<String> participantGameIds = this.getHistoryByPuuidQueueDateNumber(participant.getPuuid(),
                        queue, dateLimiteRecherche.toEpochSecond(ZoneOffset.UTC),
                        dateCreationGame.toEpochSecond(ZoneOffset.UTC), nbGame, region);
                List<Game> gameParticipantLst = this.getGameListByGameIdList(participantGameIds, participant.getPuuid(),
                        region);
                if (gameParticipantLst.isEmpty()) {
                    logger.error("Erreur lors de la récupératoin des games par id");
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                // on comptabilise le nombre de win/loose pour chaque participant
                for (Game gameParticipant : gameParticipantLst) {
                    if (gameParticipant.isWin()) {
                        participant.incrementTotalWin();
                    } else {
                        participant.incrementTotalLoose();
                    }
                }
                result.add(participant);
            } catch (NotResultException e) {
                logger.warn(String.format("L'historique du participant %s n'a pas pu être retrouvé",
                        participant.getPuuid()));
            }
        }
    }

}
