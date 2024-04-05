package alissouali.amdalatjinadu.crossword.model;

public class CrosswordSquare {

    private char solution;
    private char propostion;
    private boolean horizontale;
    private boolean statut;

    public CrosswordSquare(char solution, char propostion, boolean horizontale, boolean statut) {
        this.solution = solution;
        this.propostion = propostion;
        this.horizontale = horizontale;
        this.statut = statut;
    }

    public CrosswordSquare() {
    }

    public char getSolution() {
        return solution;
    }

    public void setSolution(char solution) {
        this.solution = solution;
    }

    public char getPropostion() {
        return propostion;
    }


    public void setPropostion(char propostion) {
        this.propostion = propostion;
    }

    public boolean getHorizontale() {
        return horizontale;
    }

    public void setHorizontale(boolean horizontale) {
        this.horizontale = horizontale;
    }

    public boolean getStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

}