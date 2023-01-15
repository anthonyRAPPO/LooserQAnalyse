package apony.lol.LooserQAnalyse.service.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.model.Game;
import apony.lol.LooserQAnalyse.model.Recap;
import apony.lol.LooserQAnalyse.model.enumeration.Queue;

public interface IGameService {

    List<String> getHistoryByPuuidQueueDateNumber(String userPuuid, Queue queue, LocalDateTime dateDebut,
            LocalDateTime dateFin, int count) throws NotResultException;

    Game getGameById(String gameid) throws NotResultException;

    List<Game> getGameListByGameIdList(List<String> userGameIds) throws NotResultException;

    void fillRecapByLstGame(List<Game> gameUserLst, String userPuuid, Recap recap, boolean b);

    int getTeamIdFromPuuid(Game selectedGame, String userPuuid);

}
