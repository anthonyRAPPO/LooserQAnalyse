package apony.lol.LooserQAnalyse.service.interfaces;

import java.util.List;

import apony.lol.LooserQAnalyse.exception.NotResultException;
import apony.lol.LooserQAnalyse.model.Game;
import apony.lol.LooserQAnalyse.model.Participant;
import apony.lol.LooserQAnalyse.model.Recap;
import apony.lol.LooserQAnalyse.model.enumeration.Platform;
import apony.lol.LooserQAnalyse.model.enumeration.Queue;
import apony.lol.LooserQAnalyse.model.enumeration.Region;

public interface IGameService {

        List<String> getHistoryByPuuidQueueDateNumber(String userPuuid, Queue queue, long dateDebutEpochSecond,
                        long dateFinEpochSecond, int count, Region region) throws NotResultException;

        Game getGameById(String gameid, String userPuuid, Region region) throws NotResultException;

        List<Game> getGameListByGameIdList(List<String> userGameIds, String userPuuid, Region region);

        void fillRecapByLstGame(List<Game> gameUserLst, String userPuuid, Recap recap, boolean b);

        int getTeamIdFromPuuid(Game selectedGame, String userPuuid);

        Participant fillParticipantForGame(Game game, Queue queue, int numberGame, Region region, Platform platform,
                        Participant participant);

}
