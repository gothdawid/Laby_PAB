package com.db.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbConnection {
    private final String connectionString;
    private Connection connection;

    public DbConnection() {
        Properties appProps = new Properties();
        try {
            appProps.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String login = appProps.getProperty("login");
        String password = appProps.getProperty("password");
        String ip = appProps.getProperty("ip");
        String port = appProps.getProperty("port");
        String database = appProps.getProperty("database");

        connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?user=" + login + "&password=" + password;
    }

    public DbConnection(String ip, String port, String database, String login, String password, String driver) {
        connectionString = "jdbc:" + driver + "://" + ip + ":" + port + "/" + database + "?user=" + login + "&password=" + password;
    }

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




    public ResultSet executeQuery(String query) {
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

