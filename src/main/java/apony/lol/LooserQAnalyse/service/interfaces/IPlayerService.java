package apony.lol.LooserQAnalyse.service.interfaces;

import apony.lol.LooserQAnalyse.model.enumeration.Regions;

public interface IPlayerService {
    String getPlayerPuuidByNameAndRegion(String name, Regions region);
}
