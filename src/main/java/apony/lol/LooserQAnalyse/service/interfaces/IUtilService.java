package apony.lol.LooserQAnalyse.service.interfaces;

import java.util.List;

import apony.lol.LooserQAnalyse.model.Participant;
import apony.lol.LooserQAnalyse.model.enumeration.Platform;
import apony.lol.LooserQAnalyse.model.enumeration.Region;

public interface IUtilService {

    List<String> convertResponseToListString(String res);

    Region getRegionByPlatform(Platform platform);

    void fillParticipantElo(Participant participant);

}
