package apony.lol.LooserQAnalyse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

@CrossOrigin(origins = "*")
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

    @PostMapping("/{queue}:{platform}:{numberGame}")
    public List<Participant> getParticipantByGames(@RequestBody List<Game> gameLst, @PathVariable("queue") Queue queue,
            @PathVariable("platform") Platform platform, @PathVariable("numberGame") int numberGame) {
        if (!isRequestCorrect(gameLst, queue, platform, numberGame)) {
            logger.warn("Une requete incorrecte à été receptionnée");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect variable sent");
        }
        Region region = utilService.getRegionByPlatform(platform);
        if (Objects.isNull(region)) {
            logger.warn("Une requete à été effectuée avec une variable platform incorrect");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect variable sent");
        }
        List<Participant> result = new ArrayList<>();
        for (Game game : gameLst) {
            gameService.fillParticipantLstForGame(result, game, queue, numberGame, region);
        }
        return result;
    }

    private boolean isRequestCorrect(List<Game> gameLst, Queue queue, Platform platform, int numberGame) {
        return (Objects.nonNull(gameLst) && !gameLst.isEmpty() && Objects.nonNull(queue) && Objects.nonNull(platform)
                && numberGame > 0 && numberGame < 20);
    }

}
