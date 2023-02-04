package apony.lol.LooserQAnalyse.model;

public class Participant {
    private String puuid;
    private int teamId;
    private boolean win;
    private int totalWin;
    private int totalLoose;
    private String championPlayed;
    private String summonerName;
    private String teamPosition;
    private boolean isAlly;

    public Participant() {
        this.totalWin = 0;
        this.totalLoose = 0;
    }

    public Participant(String puuid, int teamId, boolean win, String championPlayed, String summonerName,
            String teamPosition, boolean isAlly) {
        this.puuid = puuid;
        this.teamId = teamId;
        this.win = win;
        this.championPlayed = championPlayed;
        this.summonerName = summonerName;
        this.teamPosition = teamPosition;
        this.totalWin = 0;
        this.totalLoose = 0;
        this.isAlly = isAlly;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getTotalWin() {
        return totalWin;
    }

    public void incrementTotalWin() {
        this.totalWin++;
        ;
    }

    public int getTotalLoose() {
        return totalLoose;
    }

    public void incrementTotalLoose() {
        this.totalLoose++;
    }

    public String getChampionPlayed() {
        return championPlayed;
    }

    public void setChampionPlayed(String championPlayed) {
        this.championPlayed = championPlayed;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getTeamPosition() {
        return teamPosition;
    }

    public void setTeamPosition(String teamPosition) {
        this.teamPosition = teamPosition;
    }

    public void setTotalWin(int totalWin) {
        this.totalWin = totalWin;
    }

    public void setTotalLoose(int totalLoose) {
        this.totalLoose = totalLoose;
    }

    public boolean isAlly() {
        return isAlly;
    }

    public void setAlly(boolean isAlly) {
        this.isAlly = isAlly;
    }

}
