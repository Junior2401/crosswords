package alissouali.amdalatjinadu.crossword.model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private Connection connection;

    public Database() {
        try {
            connection = connectToDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  Connection connectToDB() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost/crossword?useSSL=false", "root", "");
        return this.connection;
    }

    public ResultSet availableGrids() throws SQLException {
        // Implémentez cette méthode pour retourner une Map<Integer, String> des grilles disponibles
        Statement stmt = null;
        try {
            stmt = connectToDB().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String s = "SELECT * FROM GRID";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rs;
    }

    public ResultSet extractGrid(int numGrille) throws SQLException {
        Statement stmt = null;
        try {
            stmt = connectToDB().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String s = "SELECT * FROM GRID WHERE numero_grille="+numGrille+" ";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public ResultSet getGridData(int numGrille) throws SQLException {
        Statement stmt = null;
        try {
            stmt = connectToDB().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String s = "SELECT * FROM CROSSWORD WHERE numero_grille="+numGrille+" ";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

  /* public static void main(String[] args) throws SQLException {
        Database database = new Database();
        System.out.println(database.availableGrids());
    }*/
}
