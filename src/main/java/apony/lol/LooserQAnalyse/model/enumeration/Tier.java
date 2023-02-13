package apony.lol.LooserQAnalyse.model.enumeration;

public enum Tier {
    IRON(1),
    BRONZE(400),
    SILVER(800),
    GOLD(1200),
    PLATINUM(1600),
    DIAMOND(2000),
    MASTER(2400),
    GRANDMASTER(2400),
    CHALLENGER(2400),
    EMPTY(0);

    private final int elo;

    private Tier(int elo) {
        this.elo = elo;
    }

    public int getElo() {
        return elo;
    }
}
