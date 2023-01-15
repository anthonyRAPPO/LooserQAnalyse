package apony.lol.LooserQAnalyse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.model.enumeration.Platform;
import apony.lol.LooserQAnalyse.service.interfaces.IGameService;
import apony.lol.LooserQAnalyse.service.interfaces.IPlayerService;

@RestController
public class TestController2 {

    @Autowired
    IPlayerService playerService;

    @Autowired
    IGameService gameService;

    @GetMapping("/")
    public List<String> index() throws NotResultException {
        String userPuuid = playerService.getPlayerPuuidByNameAndPlatform("Apony", Platform.EUW);
        List<String> userGameIds = gameService.getHistoryByPuuidAndNumber(userPuuid, 20);
        return userGameIds;
    }
}
