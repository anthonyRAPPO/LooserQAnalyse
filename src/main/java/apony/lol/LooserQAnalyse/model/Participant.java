package apony.lol.LooserQAnalyse.model;

public class Participant {
    private String puuid;
    private int teamId;
    private boolean win;

    public Participant() {
    }

    public Participant(String puuid, int teamId, boolean win) {
        this.puuid = puuid;
        this.teamId = teamId;
        this.win = win;
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

}
