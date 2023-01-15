package apony.lol.LooserQAnalyse.model.enumeration;

public enum Platform {
    BR("br1.api.riotgames.com"),
    EUN("eun1.api.riotgames.com"),
    EUW("euw1.api.riotgames.com"),
    JP("jp1.api.riotgames.com"),
    KR("kr.api.riotgames.com"),
    LAN("la1.api.riotgames.com"),
    LAS("la2.api.riotgames.com"),
    NA("na1.api.riotgames.com"),
    OCE("oc1.api.riotgames.com"),
    TR("tr1.api.riotgames.com"),
    RU("ru.api.riotgames.com");

    private final String path;

    private Platform(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
