package alissouali.amdalatjinadu.crossword.controller;

import alissouali.amdalatjinadu.crossword.CrosswordApplication;
import alissouali.amdalatjinadu.crossword.model.Database;
import alissouali.amdalatjinadu.crossword.model.GridModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class GridController {
    Database database = new Database();
    GridModel welcome = new GridModel(database);
    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Crossword game!");
    }

    @FXML
    public void initialize() throws SQLException {
        comboBox.setItems(welcome.gridList());
    }

    public void redirectTo(ActionEvent event) throws IOException, SQLException {
        String selectedItem = comboBox.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int selectedId = Integer.parseInt(selectedItem.split(" - ")[0]);
            // Charger la nouvelle scène en utilisant l'ID sélectionné
            //FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/resources/alissouali/amdalatjinadu/crossword/crossword-view.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(CrosswordApplication.class.getResource("crossword-view.fxml"));

            // Définir une fabrique de contrôleur pour passer les paramètres
            fxmlLoader.setControllerFactory(controllerClass -> {
                if (controllerClass == CrosswordController.class) {
                    return new CrosswordController(selectedId);
                } else {
                    try {
                        return controllerClass.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });




            // Créer une nouvelle scène avec la racine du crossword IHM
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(scene);
            stage.show();
        }
    }


    public void quitterIHM(ActionEvent event){
        // Obtenez la fenêtre actuelle en utilisant l'événement
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Fermez la fenêtre
        stage.close();
    }



}