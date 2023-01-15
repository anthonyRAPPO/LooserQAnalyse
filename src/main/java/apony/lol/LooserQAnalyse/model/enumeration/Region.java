package apony.lol.LooserQAnalyse.model.enumeration;

public enum Region {
    AMERICAS("americas.api.riotgames.com"),
    ASIA("asia.api.riotgames.com"),
    EUROPE("europe.api.riotgames.com"),
    SEA("sea.api.riotgames.com");

    private final String path;

    private Region(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
