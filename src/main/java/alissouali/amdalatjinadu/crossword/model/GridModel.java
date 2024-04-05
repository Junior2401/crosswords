package alissouali.amdalatjinadu.crossword.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GridModel {

    private Database database;
    public GridModel(Database database) {
        this.database = database;
    }



    public ObservableList gridList() throws SQLException {
        ResultSet rs =   this.database.availableGrids();
        if (rs.next()) {
            ObservableList<String> data = FXCollections.observableArrayList();

            while (rs.next()){
                System.out.println(rs.getFetchSize());
                int id = rs.getInt("numero_grille");
                String nom = rs.getString("nom_grille");
                int largeur = rs.getInt("largeur");
                int hauteur = rs.getInt("hauteur");

                data.add(id + " - " + nom + " - " + "("+ hauteur+ ", "+largeur+")");

            }
            System.out.println(data.toString());
            return  data;
        }else
            return  null;
    }


}
