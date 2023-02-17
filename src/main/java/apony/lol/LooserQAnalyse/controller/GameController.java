package apony.lol.LooserQAnalyse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.model.Game;
import apony.lol.LooserQAnalyse.model.enumeration.Platform;
import apony.lol.LooserQAnalyse.model.enumeration.Queue;
import apony.lol.LooserQAnalyse.model.enumeration.Region;
import apony.lol.LooserQAnalyse.service.interfaces.IGameService;
import apony.lol.LooserQAnalyse.service.interfaces.IPlayerService;
import apony.lol.LooserQAnalyse.service.interfaces.IUtilService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    IPlayerService playerService;

    @Autowired
    IGameService gameService;

    @Autowired
    IUtilService utilService;

    Logger logger = LoggerFactory.getLogger(GameController.class);

    @GetMapping("/test")
    public void test() {
        System.out.println("YES");
    }

    @GetMapping("/{login}:{queue}:{dateDebut}:{dateFin}:{count}:{platform}")
    public List<Game> getHistoryByLoginQueueDateCountRegion(@PathVariable("login") String login,
            @PathVariable("queue") Queue queue,
            @PathVariable("dateDebut") long dateDebutEpochSecond, @PathVariable("dateFin") long dateFinEpochSecond,
            @PathVariable("count") int count, @PathVariable("platform") Platform platform) {
        if (!isRequestCorrect(login, queue, dateDebutEpochSecond, dateFinEpochSecond, count, platform)) {
            logger.warn("Une requete incorrecte à été receptionnée");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect variable sent");
        }
        Region region = utilService.getRegionByPlatform(platform);
        if (Objects.isNull(region)) {
            logger.warn("Une requete à été effectuée avec une variable platform incorrect");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect variable sent");
        }
        try {
            String userPuuid = playerService.getPlayerPuuidByNameAndPlatform(login, platform);
            if (Objects.isNull(userPuuid) || userPuuid.isEmpty()) {
                // aucun joueur trouvé pour ce login
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No player found");
            }
            try {
                List<String> userGameIds = gameService.getHistoryByPuuidQueueDateNumber(userPuuid, queue,
                        dateDebutEpochSecond,
                        dateFinEpochSecond, count, region);
                return gameService.getGameListByGameIdList(userGameIds, userPuuid, region);
            } catch (NotResultException e) {
                // aucune partie trouvée dans l'historique avec les param donnés
                return new ArrayList<Game>();
            }

        } catch (NotResultException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No player found");
        }
    }

    private boolean isRequestCorrect(String login, Queue queue, long dateDebut, long dateFin,
            int count, Platform platform) {
        return (Objects.nonNull(login) && !login.isEmpty() && Objects.nonNull(queue) && count > 0 && count <= 20
                && dateDebut > 0 && dateFin > 0 && Objects.nonNull(platform));
    }
}
