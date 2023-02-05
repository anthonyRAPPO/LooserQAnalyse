package apony.lol.LooserQAnalyse.model.enumeration;

public enum Rank {
    I(300), II(200), III(100), IV(0), V(0);

    private final int elo;

    private Rank(int elo) {
        this.elo = elo;
    }

    public int getElo() {
        return elo;
    }

}
