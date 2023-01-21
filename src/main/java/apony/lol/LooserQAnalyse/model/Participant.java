package apony.lol.LooserQAnalyse.model;

public class Participant {
    private String puuid;
    private int teamId;
    private boolean win;
    private int totalWin;
    private int totalLoose;

    public Participant() {
        this.totalWin = 0;
        this.totalLoose = 0;
    }

    public Participant(String puuid, int teamId, boolean win) {
        this.puuid = puuid;
        this.teamId = teamId;
        this.win = win;
        this.totalWin = 0;
        this.totalLoose = 0;
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

}
