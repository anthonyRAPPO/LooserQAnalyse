package apony.lol.LooserQAnalyse.model;

import java.util.List;

public class Game {
    private String id;
    private List<Participant> lstParticipants;
    private long timeStampCreation;

    public Game() {
    }

    public Game(String id, List<Participant> lstParticipants, long timeStampCreation) {
        this.id = id;
        this.lstParticipants = lstParticipants;
        this.timeStampCreation = timeStampCreation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Participant> getLstParticipants() {
        return lstParticipants;
    }

    public void setLstParticipants(List<Participant> lstParticipants) {
        this.lstParticipants = lstParticipants;
    }

    public long getTimeStampCreation() {
        return timeStampCreation;
    }

    public void setTimeStampCreation(long timeStampCreation) {
        this.timeStampCreation = timeStampCreation;
    }

}
