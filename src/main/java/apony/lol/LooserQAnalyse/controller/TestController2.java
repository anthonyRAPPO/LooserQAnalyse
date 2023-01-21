package apony.lol.LooserQAnalyse.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.model.Game;
import apony.lol.LooserQAnalyse.model.Participant;
import apony.lol.LooserQAnalyse.model.Recap;
import apony.lol.LooserQAnalyse.model.enumeration.Platform;
import apony.lol.LooserQAnalyse.model.enumeration.Queue;
import apony.lol.LooserQAnalyse.service.interfaces.IGameService;
import apony.lol.LooserQAnalyse.service.interfaces.IPlayerService;

@RestController
public class TestController2 {

    @Autowired
    IPlayerService playerService;

    @Autowired
    IGameService gameService;

    @GetMapping("/")
    public Recap index() throws NotResultException {
        /*
         * int nbGame = 10;
         * int nbJourMax = 100;
         * Queue queue = Queue.RANKED_SOLO;
         * String userPuuid = playerService.getPlayerPuuidByNameAndPlatform("Apony",
         * Platform.EUW);
         * LocalDateTime dateFin = LocalDateTime.now();
         * LocalDateTime dateDebut = dateFin.minusDays(nbJourMax);
         * List<String> userGameIds =
         * gameService.getHistoryByPuuidQueueDateNumber(userPuuid, queue, dateDebut,
         * dateFin, nbGame);
         * List<Game> gameUserLst = gameService.getGameListByGameIdList(userGameIds,
         * userPuuid);
         * Game selectedGame = gameUserLst.get(0);
         * int teamId = gameService.getTeamIdFromPuuid(selectedGame, userPuuid);
         * Recap recap = new Recap();
         * gameService.fillRecapByLstGame(gameUserLst, userPuuid, recap, true);
         * LocalDateTime dateCreationGame =
         * LocalDateTime.ofEpochSecond(selectedGame.getTimeStampCreation(), 0,
         * ZoneOffset.UTC);
         * LocalDateTime dateLimiteRecherche = dateCreationGame.minusDays(nbJourMax);
         * for (Participant participant : selectedGame.getLstParticipants()) {
         * boolean isAlly = (participant.getTeamId() == teamId);
         * List<String> participantGameIds =
         * gameService.getHistoryByPuuidQueueDateNumber(participant.getPuuid(),
         * queue, dateLimiteRecherche, dateCreationGame, nbGame);
         * List<Game> gameParticipantLst =
         * gameService.getGameListByGameIdList(participantGameIds, userPuuid);
         * gameService.fillRecapByLstGame(gameParticipantLst, participant.getPuuid(),
         * recap, isAlly);
         * 
         * }
         * 
         * return recap;
         */
        return null;
    }
}
