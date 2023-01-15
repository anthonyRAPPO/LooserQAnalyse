package apony.lol.LooserQAnalyse.service.interfaces;

import java.util.List;

import apony.lol.LooserQAnalyse.exception.NotResultException;

public interface IGameService {

    List<String> getHistoryByPuuidAndNumber(String userPuuid, int count) throws NotResultException;

}
