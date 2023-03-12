package apony.lol.LooserQAnalyse.model;

public class PlayerInfo {
    private String puuid;
    private String summonerId;

    public PlayerInfo() {
    }

    public PlayerInfo(String puuid, String summonerId) {
        this.puuid = puuid;
        this.summonerId = summonerId;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

}
