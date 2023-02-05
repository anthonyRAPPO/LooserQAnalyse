package apony.lol.LooserQAnalyse.model.enumeration;

public enum Tier {
    IRON(0),
    BRONZE(400),
    SIVLER(800),
    GOLD(1200),
    PLATINUM(1600),
    DIAMOND(2000),
    MASTER(2400),
    GRANDMASTER(2400),
    CHALLENGER(2400);

    private final int elo;

    private Tier(int elo) {
        this.elo = elo;
    }

    public int getElo() {
        return elo;
    }
}
