package apony.lol.LooserQAnalyse.model;

public class Recap {
    private int nbVictoryAlly;
    private int nbLooseAlly;
    private int nbVictoryEnnemy;
    private int nbLooseEnnemy;
    private int totalGameAlly;
    private int totalGameEnnemy;

    public Recap() {
        this.nbVictoryAlly = 0;
        this.nbLooseAlly = 0;
        this.nbVictoryEnnemy = 0;
        this.nbLooseEnnemy = 0;
        this.totalGameAlly = 0;
        this.totalGameEnnemy = 0;
    }

    public int getNbVictoryAlly() {
        return nbVictoryAlly;
    }

    public void incrementNbVictoryAlly() {
        this.nbVictoryAlly++;
    }

    public int getNbLooseAlly() {
        return nbLooseAlly;
    }

    public void incrementNbLooseAlly() {
        this.nbLooseAlly++;
    }

    public int getNbVictoryEnnemy() {
        return nbVictoryEnnemy;
    }

    public void incrementNbVictoryEnnemy() {
        this.nbVictoryEnnemy++;
    }

    public int getNbLooseEnnemy() {
        return nbLooseEnnemy;
    }

    public void incrementNbLooseEnnemy() {
        this.nbLooseEnnemy++;
    }

    public int getTotalGameAlly() {
        return totalGameAlly;
    }

    public void incrementTotalGameAlly() {
        this.totalGameAlly++;
    }

    public int getTotalGameEnnemy() {
        return totalGameEnnemy;
    }

    public void incrementTotalGameEnnemy() {
        this.totalGameEnnemy++;
    }

}
