package by.dyagel.model.DB;

import java.sql.*;

/**
 * This class works with database
 */
abstract public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    protected void update(int id, String sql) {
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ResultSet get(int id, String sql) {
        ResultSet resSet = null;
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1, id);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    ResultSet getAll(String table) {
        ResultSet resSet = null;
        PreparedStatement prSt = null;
        String sql = "SELECT * FROM " + table;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
}

