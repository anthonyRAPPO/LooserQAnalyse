package apony.lol.LooserQAnalyse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apony.lol.LooserQAnalyse.model.Game;
import apony.lol.LooserQAnalyse.model.Participant;
import apony.lol.LooserQAnalyse.model.enumeration.Platform;
import apony.lol.LooserQAnalyse.model.enumeration.Queue;
import apony.lol.LooserQAnalyse.model.enumeration.Region;
import apony.lol.LooserQAnalyse.service.interfaces.IGameService;
import apony.lol.LooserQAnalyse.service.interfaces.IPlayerService;
import apony.lol.LooserQAnalyse.service.interfaces.IUtilService;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

    @Autowired
    IPlayerService playerService;

    @Autowired
    IGameService gameService;

    @Autowired
    IUtilService utilService;

    Logger logger = LoggerFactory.getLogger(ParticipantController.class);

    @PostMapping("/{queue}:{platform}:{numberGame}:{participantId}")
    public Participant fillParticipantByGame(@RequestBody Game game, @PathVariable("queue") Queue queue,
            @PathVariable("platform") Platform platform, @PathVariable("numberGame") int numberGame,
            @PathVariable("participantId") String participantId) {

        if (!isRequestCorrect(game, queue, platform, numberGame, participantId)) {
            logger.warn("Une requete incorrecte à été receptionnée");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect variable sent");
        }
        List<Participant> lstparticipant = game.getLstParticipants().stream()
                .filter(p -> p.getId().equals(participantId)).collect(Collectors.toList());
        Region region = utilService.getRegionByPlatform(platform);
        if (Objects.isNull(region) || Objects.isNull(lstparticipant) ||
                lstparticipant.size() != 1) {
            logger.warn("Une requete à été effectuée avec une variable platform incorrect");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect variable sent");
        }

        return gameService.fillParticipantForGame(game, queue, numberGame, region,
                platform, lstparticipant.get(0));

    }

    private boolean isRequestCorrect(Game game, Queue queue, Platform platform, int numberGame, String participantId) {
        return (Objects.nonNull(game) && Objects.nonNull(queue) && Objects.nonNull(platform)
                && Objects.nonNull(participantId) && !participantId.isEmpty()
                && numberGame > 0 && numberGame < 20);
    }

}
