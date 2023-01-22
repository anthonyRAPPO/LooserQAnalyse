package apony.lol.LooserQAnalyse.model;

import java.util.List;

public class Game {
    private String id;
    private List<Participant> lstParticipants;
    private long timeStampCreation;
    private int allyTeam;
    private boolean win;
    private String championPlayed;
    private String uuidPlayer;
    private int assists;
    private int deaths;
    private int kills;

    public Game() {
    }

    public Game(String id, List<Participant> lstParticipants, long timeStampCreation, int allyTeam,
            boolean win, String championPlayed, String uuidPlayer, int assists, int deaths, int kills) {
        this.id = id;
        this.lstParticipants = lstParticipants;
        this.timeStampCreation = timeStampCreation;
        this.allyTeam = allyTeam;
        this.win = win;
        this.championPlayed = championPlayed;
        this.uuidPlayer = uuidPlayer;
        this.assists = assists;
        this.deaths = deaths;
        this.kills = kills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Participant> getLstParticipants() {
        return lstParticipants;
    }

    public void setLstParticipants(List<Participant> lstParticipants) {
        this.lstParticipants = lstParticipants;
    }

    public long getTimeStampCreation() {
        return timeStampCreation;
    }

    public void setTimeStampCreation(long timeStampCreation) {
        this.timeStampCreation = timeStampCreation;
    }

    public int getAllyTeam() {
        return allyTeam;
    }

    public void setAllyTeam(int allyTeam) {
        this.allyTeam = allyTeam;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public String getChampionPlayed() {
        return championPlayed;
    }

    public void setChampionPlayed(String championPlayed) {
        this.championPlayed = championPlayed;
    }

    public String getUuidPlayer() {
        return uuidPlayer;
    }

    public void setUuidPlayer(String uuidPlayer) {
        this.uuidPlayer = uuidPlayer;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

}
