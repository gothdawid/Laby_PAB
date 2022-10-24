package com.db.api;

import java.sql.*;

public class DbConnection implements IDbConnection {
    private final String connectionString;
    private Connection connection;

    public DbConnection(String username, String password, String driver, String url, String database, String port) {
        this.connectionString = "jdbc:" + driver + "://" + url + ":" + port + "/" + database + "?user=" + username + "&password=" + password;
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(connectionString);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet query(String query) {
        ResultSet result;
        try {
            PreparedStatement pS =  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            result = pS.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

}

