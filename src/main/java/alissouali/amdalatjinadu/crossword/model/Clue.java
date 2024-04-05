package alissouali.amdalatjinadu.crossword.model;

public class Clue {
private String clue;
private int row;
private int column;
private boolean horizontal;

    /**
     * Constructeur de la classe
     * @param clue
     * @param row
     * @param column
     * @param horizontal
     */
    public Clue(String clue, int row, int column, boolean horizontal){
        this.clue = clue;
        this.row = row;
        this.column = column;
        this.horizontal = horizontal;
    }

    /**
     * getteur de la variable clue
     * @return
     */
    public String getClue(){

        return this.clue;
    }

    /**
     * getteur de la colonne
     * @return
     */
    public int getColumn(){
        return this.column;
    }

    /**
     * getteur de la ligne
     * @return
     */
    public int getRow(){
        return  this.row;
    }


    @Override
    public String toString(){
        return" "+getClue()+" ("+getRow()+","+getColumn()+")";
    }
}