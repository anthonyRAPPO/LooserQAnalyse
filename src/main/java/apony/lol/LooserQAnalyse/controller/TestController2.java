package apony.lol.LooserQAnalyse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import apony.lol.LooserQAnalyse.model.enumeration.Regions;
import apony.lol.LooserQAnalyse.service.interfaces.IPlayerService;

@RestController
public class TestController2 {

    @Autowired
    IPlayerService playerService;

    @GetMapping("/")
    public String index() {
        String userPuuid = playerService.getPlayerPuuidByNameAndRegion("Apony", Regions.EUW);
        return userPuuid;
    }
}
