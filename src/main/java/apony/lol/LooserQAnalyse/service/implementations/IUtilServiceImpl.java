package apony.lol.LooserQAnalyse.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import apony.lol.LooserQAnalyse.model.enumeration.Platform;
import apony.lol.LooserQAnalyse.model.enumeration.Region;
import apony.lol.LooserQAnalyse.service.interfaces.IUtilService;

@Service
public class IUtilServiceImpl implements IUtilService {

    @Override
    public List<String> convertResponseToListString(String res) {
        List<String> lstString = new ArrayList<>();
        String[] resTab = res.substring(1, res.length() - 1).split(",");
        for (String resString : resTab) {
            lstString.add(resString.replace("\"", ""));
        }
        return lstString;
    }

    @Override
    public Region getRegionByPlatform(Platform platform) {
        switch (platform) {
            case EUW:
                return Region.EUROPE;
            case BR:
                return Region.AMERICAS;
            case EUN:
                return Region.EUROPE;
            case JP:
                return Region.ASIA;
            case KR:
                return Region.ASIA;
            case LAN:
                return Region.AMERICAS;
            case LAS:
                return Region.AMERICAS;
            case NA:
                return Region.AMERICAS;
            case OCE:
                return Region.SEA;
            case RU:
                return Region.EUROPE;
            case TR:
                return Region.EUROPE;
        }
        return null;
    }

}
