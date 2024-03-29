package apony.lol.LooserQAnalyse.model;

import apony.lol.LooserQAnalyse.model.enumeration.Rank;
import apony.lol.LooserQAnalyse.model.enumeration.Tier;

public class Participant {
    private String id;
    private String puuid;
    private int teamId;
    private boolean win;
    private int totalWin;
    private int totalLoose;
    private String championPlayed;
    private String summonerName;
    private String teamPosition;
    private boolean isAlly;
    private Tier tier;
    private Rank rank;
    private int leaguePoints;
    private int totalWinSeason;
    private int totalLooseSeason;
    private int calculatedElo;
    private int kill;
    private int death;
    private int assist;
    private float kda;
    private int gold;
    private int dmgDealt;
    private int dmgTaken;
    private int visionScore;
    private int totalcs;
    private int longestTimeLiving;
    private int totalKill;
    private int totalDeath;
    private int totalAssist;
    private float totalKda;

    public Participant() {
        this.totalWin = 0;
        this.totalLoose = 0;
        this.totalKill = 0;
        this.totalDeath = 0;
        this.totalAssist = 0;
    }

    public Participant(String id, String puuid, int teamId, boolean win, int totalWin, int totalLoose,
            String championPlayed, String summonerName, String teamPosition, boolean isAlly, Tier tier, Rank rank,
            int leaguePoints, int totalWinSeason, int totalLooseSeason, int calculatedElo, int kill, int death,
            int assist, float kda, int totalKill, int totalDeath, int totalAssist, float totalKda) {
        this.id = id;
        this.puuid = puuid;
        this.teamId = teamId;
        this.win = win;
        this.totalWin = totalWin;
        this.totalLoose = totalLoose;
        this.championPlayed = championPlayed;
        this.summonerName = summonerName;
        this.teamPosition = teamPosition;
        this.isAlly = isAlly;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
        this.totalWinSeason = totalWinSeason;
        this.totalLooseSeason = totalLooseSeason;
        this.calculatedElo = calculatedElo;
        this.kill = kill;
        this.death = death;
        this.assist = assist;
        this.kda = kda;
        this.totalDeath = totalDeath;
        this.totalKill = totalKill;
        this.totalAssist = totalAssist;
        this.totalKda = totalKda;
    }

    public int getTotalKill() {
        return totalKill;
    }

    public void setTotalKill(int totalKill) {
        this.totalKill = totalKill;
    }

    public int getTotalDeath() {
        return totalDeath;
    }

    public void setTotalDeath(int totalDeath) {
        this.totalDeath = totalDeath;
    }

    public int getTotalAssist() {
        return totalAssist;
    }

    public void setTotalAssist(int totalAssist) {
        this.totalAssist = totalAssist;
    }

    public float getTotalKda() {
        return totalKda;
    }

    public void setTotalKda(float totalKda) {
        this.totalKda = totalKda;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public int getTotalWinSeason() {
        return totalWinSeason;
    }

    public void setTotalWinSeason(int totalWinSeason) {
        this.totalWinSeason = totalWinSeason;
    }

    public int getTotalLooseSeason() {
        return totalLooseSeason;
    }

    public void setTotalLooseSeason(int totalLooseSeason) {
        this.totalLooseSeason = totalLooseSeason;
    }

    public int getCalculatedElo() {
        return calculatedElo;
    }

    public void setCalculatedElo(int calculatedElo) {
        this.calculatedElo = calculatedElo;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public float getKda() {
        return kda;
    }

    public void setKda(float kda) {
        this.kda = kda;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDmgDealt() {
        return dmgDealt;
    }

    public void setDmgDealt(int dmgDealt) {
        this.dmgDealt = dmgDealt;
    }

    public int getDmgTaken() {
        return dmgTaken;
    }

    public void setDmgTaken(int dmgTaken) {
        this.dmgTaken = dmgTaken;
    }

    public int getVisionScore() {
        return visionScore;
    }

    public void setVisionScore(int visionScore) {
        this.visionScore = visionScore;
    }

    public int getTotalcs() {
        return totalcs;
    }

    public void setTotalcs(int totalcs) {
        this.totalcs = totalcs;
    }

    public int getLongestTimeLiving() {
        return longestTimeLiving;
    }

    public void setLongestTimeLiving(int longestTimeLiving) {
        this.longestTimeLiving = longestTimeLiving;
    }

}
