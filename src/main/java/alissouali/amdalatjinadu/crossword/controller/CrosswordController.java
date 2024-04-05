package alissouali.amdalatjinadu.crossword.controller;

import alissouali.amdalatjinadu.crossword.model.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

public class CrosswordController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Bienvenue au jeu de mot Crossword");
    }

    @FXML
    private GridPane gridpane;

    public CrosswordController(Label welcomeText) {
        this.welcomeText = welcomeText;
    }

    @FXML
    private ListView<Clue> indicesHorizontaux;

    @FXML
    private ListView<Clue> indicesVerticaux;

    private int selectId;

    private boolean currentDirectionIsHorizontal = false;
    public int lastFocusPosition = 0;
    private Crossword game;


    public CrosswordController(int selectId) {
        this.selectId = selectId;
    }

    public void initialize() {
        Database db = new Database();
        try {
            System.out.println("Init....");
            game = Crossword.createPuzzle(db, this.selectId);
            System.out.println(game);

            this.indicesHorizontaux.setItems(game.getHorizontalClues());
            this.indicesVerticaux.setItems(game.getVerticalClues());
            this.addChangeListner();
            for(int row = 0; row <game.getWidth(); row++){
                for(int col = 0; col < game.getHeight(); col++){
                    int iterationNumber = row * game.getWidth() + col; // Calcul du numéro d'itération
                    TextFieldCase text = new TextFieldCase(game.isBlackSquare(row, col),iterationNumber,row, col , gridpane, this);
                    text.setPrefSize(100,100);
                    if(game.isBlackSquare(row, col)) {
                        text.setStyle("-fx-background-color: black;");
                        //text.setDisable(true);
                    }
                    else{
                        text.setStyle("-fx-border-color: black;-fx-border-width: 1px");
                    }
                    addListener(text, row, col);
                    gridpane.add(text, col, row);
                }
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addListener(TextField textField, int row, int col){
        // Ecouteur pour la touche Entrée
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // checkvalidSolution();
            }
        });

        // Ecouteur pour les touches directionnelles
        textField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    currentDirectionIsHorizontal = false;
                    System.out.println("Touche Haut pressée.");
                    if(lastFocusPosition - gridpane.getRowCount()>=0){
                        lastFocusPosition = lastFocusPosition - gridpane.getRowCount();
                        TextField element = (TextField) gridpane.getChildren().get(lastFocusPosition);
                        element.requestFocus();
                    }
                    break;
                case DOWN:
                    currentDirectionIsHorizontal = false;
                    System.out.println("Touche Bas pressée.");
                    if(lastFocusPosition+ gridpane.getRowCount()< gridpane.getRowCount()*gridpane.getColumnCount()){
                        lastFocusPosition = lastFocusPosition+ gridpane.getRowCount();
                        TextField element = (TextField) gridpane.getChildren().get(lastFocusPosition);
                        element.requestFocus();
                    }else System.out.println(lastFocusPosition+ gridpane.getRowCount());
                    break;
                case LEFT:
                    currentDirectionIsHorizontal = true;
                    System.out.println("Touche Gauche pressée.");
                    if(lastFocusPosition -1 >=0){
                        lastFocusPosition--;
                        TextField element = (TextField) gridpane.getChildren().get(lastFocusPosition);
                        element.requestFocus();
                    }
                    break;
                case RIGHT:
                    currentDirectionIsHorizontal = true;
                    System.out.println("Touche Droite pressée.");
                    if(lastFocusPosition+1< gridpane.getRowCount()*gridpane.getColumnCount()){
                        lastFocusPosition++;
                        TextField element = (TextField) gridpane.getChildren().get(lastFocusPosition);
                        element.requestFocus();
                    }
                    break;
                default:
                    break;
            }
        });
    }
    public  void setLastFocusPosition(int val){
        lastFocusPosition = val;

    }

    public ListView<Clue> getIndicesHorizontaux() {
        return indicesHorizontaux;
    }

    public ListView<Clue> getIndicesVerticaux() {
        return indicesVerticaux;
    }
    public Crossword getCrossword(){
        return  game;
    }

    private void addChangeListner(){
        // Création du ChangeListener
        ChangeListener<Clue> selectionListener = new ChangeListener<Clue>() {
            @Override
            public void changed(ObservableValue<? extends Clue> observable, Clue oldValue, Clue newValue) {
                if (newValue != null) {
                    currentDirectionIsHorizontal = true;
                    // (row - 1) * nombre de colonnes + (col - 1)
                    int index = getElementIndex(newValue.getRow(), newValue.getColumn());
                    TextField element = (TextField) gridpane.getChildren().get(index);
                    resertStyle();
                    element.setStyle("-fx-border-color: blue;");
                    Platform.runLater(() -> element.requestFocus());
                }
            }
        };
        indicesHorizontaux.getSelectionModel().selectedItemProperty().addListener(selectionListener);


        ChangeListener<Clue> selectionListener2 = new ChangeListener<Clue>() {
            @Override
            public void changed(ObservableValue<? extends Clue> observable, Clue oldValue, Clue newValue) {
                // Votre logique ici pour afficher le nom du cube sélectionné
                if (newValue != null) {
                    currentDirectionIsHorizontal = false;
                    // (row - 1) * nombre de colonnes + (col - 1)
                    int index = getElementIndex(newValue.getRow(), newValue.getColumn());
                    TextField element = (TextField) gridpane.getChildren().get(index);
                    resertStyle();
                    element.setStyle("-fx-border-color: blue;");
                    Platform.runLater(() -> element.requestFocus());
                }
            }
        };
        indicesVerticaux.getSelectionModel().selectedItemProperty().addListener(selectionListener2);


    }

    private void resertStyle(){
        for(int row = 0; row <game.getWidth(); row++){
            for(int col = 0; col < game.getHeight(); col++){
                int iterationNumber = row * game.getWidth() + col; // Calcul du numéro d'itération
                if(!game.isBlackSquare(row, col)) {
                    TextField element = (TextField) gridpane.getChildren().get(iterationNumber);
                    element.setStyle("");
                }
            }
        }
    }
    private int getElementIndex(int row, int col){
        return (row-1)* gridpane.getColumnCount() +(col-1);
    }
    // Création du ChangeListener
    private void addListener2(TextField textField, int row, int col){
        // Ecouteur pour la touche Entrée
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Touche Entrée pressée.");
            }
        });

        // Ecouteur pour les touches directionnelles
        textField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    currentDirectionIsHorizontal = false;
                    System.out.println("Touche Haut pressée.");
                    if(col>0){
                        TextField element = (TextField) gridpane.getChildren().get((col-1) * gridpane.getRowCount() + row);
                        element.requestFocus();
                    }
                    break;
                case DOWN:
                    currentDirectionIsHorizontal = false;
                    System.out.println("Touche Bas pressée.");
                    if(col< game.getHeight()-1){
                        TextField element = (TextField) gridpane.getChildren().get(((col+1) * gridpane.getRowCount() + row));
                        element.requestFocus();
                    }
                    break;
                case LEFT:
                    currentDirectionIsHorizontal = true;
                    System.out.println("Touche Gauche pressée.");
                    if(row>0){
                        TextField element = (TextField) gridpane.getChildren().get(((row-1) * gridpane.getColumnCount() + col));
                        element.requestFocus();
                    }
                    break;
                case RIGHT:
                    currentDirectionIsHorizontal = true;
                    System.out.println("Touche Droite pressée.");
                    if(row< game.getWidth()-1){
                        TextField element = (TextField) gridpane.getChildren().get(((row+1) * gridpane.getColumnCount() + col));
                        element.requestFocus();
                    }
                    break;
                default:
                    break;
            }
        });
    }

    public void moveCursorToNext() {
        if(this.currentDirectionIsHorizontal){
            if(lastFocusPosition+1< gridpane.getRowCount()*gridpane.getColumnCount()){
                lastFocusPosition++;
                TextField element = (TextField) gridpane.getChildren().get(lastFocusPosition);
                element.requestFocus();
            }
            //  textFieldCase.getRow();
        }else{
            if(lastFocusPosition+ gridpane.getRowCount()< gridpane.getRowCount()*gridpane.getColumnCount()){
                lastFocusPosition = lastFocusPosition+ gridpane.getRowCount();
                TextField element = (TextField) gridpane.getChildren().get(lastFocusPosition);
                element.requestFocus();
            }
        }
    }

    public void checkvalidSolution(){
        for(int row = 0; row <game.getWidth(); row++){
            for(int col = 0; col < game.getHeight(); col++){
                int iterationNumber = row * game.getWidth() + col; // Calcul du numéro d'itération
                TextFieldCase element = (TextFieldCase) gridpane.getChildren().get(iterationNumber);
                if(element.getText().equals(game.getCell(row,col).getSolution())) {
                    element.setStyle("-fx-background-color: green;");
                }
            }
        }
    }

    public void updateListView(int h_indice, int v_indice) {
        if(currentDirectionIsHorizontal){
            ListCell<Clue> cell = (ListCell<Clue>) this.getIndicesHorizontaux().lookup(".list-cell:nth-child(" + (h_indice + 1) + ")");
            if (cell != null) {
                cell.setStyle("-fx-background-color: red;");
            }
        }else{

            ListCell<Clue> cell = (ListCell<Clue>) this.getIndicesVerticaux().lookup(".list-cell:nth-child(" + (v_indice + 1) + ")");
            if (cell != null) {
                cell.setStyle("-fx-background-color: red;");
            }
        }


    }

}
