package apony.lol.LooserQAnalyse.model.enumeration;

public enum Queue {
    DRAFT(400),
    RANKED_SOLO(420),
    BLIND(430),
    RANKED_FLEX(440);

    private final int id;

    private Queue(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
