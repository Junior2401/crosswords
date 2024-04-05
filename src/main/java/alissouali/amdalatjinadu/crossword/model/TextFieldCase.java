package alissouali.amdalatjinadu.crossword.model;

import alissouali.amdalatjinadu.crossword.controller.CrosswordController;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class TextFieldCase extends TextField {
    private boolean isBlack = true;
    private int positionInGride = 0;
    private int row = 0;
    private int col = 0;
    private boolean currentDirectionIsHorizontal = true;
    public static int lastFocusPosition = 0;

    private GridPane gridpane;
    private CrosswordController controller;

    public TextFieldCase(boolean isBlack, int positionInGride, int row, int col, GridPane gridpane, CrosswordController controller) {
        this.isBlack = isBlack;
        this.positionInGride = positionInGride;
        this.col = col;
        this.row = row;
        this.gridpane = gridpane;
        this.controller = controller;
        //ecouter la saisie de caractere
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!isBlack){
                if (newValue.matches("[a-zA-Z]")) {
                    this.setText(newValue);
                    //deplacer le curseur dans la case suviante selon la direction courante
                    controller.moveCursorToNext();

                }
            }else{
                this.setText("");
            }

        });
        //ecouter la reception de focus
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                //donner le focus au clic dans une case
                controller.setLastFocusPosition(this.positionInGride);

                if(!isBlack){
                        //verifier la possibilité de donner des indices dans les deux listes
                       int horizontal_indice = -1;
                       int vertical_indice = -1;
                       int count = 0;
                        for (Clue clue :  controller.getCrossword().getHorizontalClues()) {
                            if (clue.getRow()==row+1 && clue.getColumn()==col+1) {
                                    horizontal_indice = count;
                                count++;
                                break; // Arrête la boucle car nous avons trouvé un élément avec name="yes"
                            }
                        }
                        count = 0;
                        for (Clue clue :  controller.getCrossword().getVerticalClues()) {
                            if (clue.getRow()==row+1 && clue.getColumn()==col+1) {
                                vertical_indice =  count;
                                count++;
                                break; // Arrête la boucle car nous avons trouvé un élément avec name="yes"
                            }
                        }

                        if(horizontal_indice!=-1 && vertical_indice!=-1){
                            System.out.println("Indice existe ="+horizontal_indice+" "+vertical_indice);
                            controller.getIndicesHorizontaux().getSelectionModel().select(horizontal_indice);
                            controller.getIndicesHorizontaux().scrollTo(horizontal_indice);

                            controller.getIndicesVerticaux().getSelectionModel().select(vertical_indice);
                            controller.getIndicesVerticaux().scrollTo(vertical_indice);

                            controller.updateListView(horizontal_indice, vertical_indice);
                        }else System.out.println("Indice existe pas "+horizontal_indice+" "+vertical_indice);

                }
            }
        });
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public void setPositionInGride(int positionInGride) {
        this.positionInGride = positionInGride;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getPositionInGride() {
        return positionInGride;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

