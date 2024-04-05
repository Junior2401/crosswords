package alissouali.amdalatjinadu.crossword.model;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Crossword extends Grid<CrosswordSquare> {
    private static ObservableList<Clue> verticalClues = FXCollections.observableArrayList();
    private static ObservableList<Clue> horizontalClues = FXCollections.observableArrayList();

    private Crossword(int height, int width){
        super(height, width);

    }



    public boolean isBlackSquare(int row, int column) {
        if(correctIndexes(row, column))
            return  getCell(row, column).getStatut();
        else
            return true;
    }

    public void setBlackSquare(int row, int column, boolean black) {
        if(correctIndexes(row, column))
            getCell(row, column).setStatut(black);
    }
    public ObservableList<Clue> getVerticalClues(){
        return verticalClues;
    }
    public ObservableList<Clue> getHorizontalClues(){
        return horizontalClues;
    }
    public static Crossword createPuzzle(Database database, int puzzleNumber) throws SQLException {
        ResultSet rs =   database.extractGrid(puzzleNumber);
         if (rs.next()) {
             int ligne = rs.getInt("hauteur");
             int colonne = rs.getInt("largeur");
             Crossword game = new Crossword(ligne, colonne);
             //init
             ResultSet rsData =   database.getGridData(puzzleNumber);
             while (rsData.next()){
                 System.out.println(puzzleNumber+" "+rsData.getFetchSize());
                 int row = rsData.getInt("ligne");
                 int col = rsData.getInt("colonne");
                 String solution = rsData.getString("solution");
                 String definition = rsData.getString("definition");
                 boolean horizontal = rsData.getBoolean("horizontal");

                 if(horizontal){
                     Crossword.horizontalClues.add(new Clue(definition, row, col, horizontal));
                 }else{
                     Crossword.verticalClues.add(new Clue(definition, row, col, horizontal));
                 }
                 fillGridWord(solution, horizontal, row-1, col-1, game);

             }
             return  game;
        }else
            return  null;
    }

    private static void fillGridWord(String solution, boolean horizontal, int row, int col, Crossword game){
        for(char c: solution.toCharArray()){
            CrosswordSquare square = new CrosswordSquare(c,'\0',  horizontal, false);
            if(horizontal){
                game.setCell(row, col, square);
                col++;
            }else{
                game.setCell(row, col, square);
                row++;
            }
        }
    }
    public void printProposition(){
    }
    public void printSolution(){

    }

    public boolean correctIndexes(int row, int col){
        if(getCell(row, col)==null)
            return false;
        else return  true;
    }
}