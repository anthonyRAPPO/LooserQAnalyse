package apony.lol.LooserQAnalyse.service.interfaces;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.model.enumeration.Platform;

public interface IPlayerService {
    String getPlayerPuuidByNameAndPlatform(String name, Platform region) throws NotResultException;
}
