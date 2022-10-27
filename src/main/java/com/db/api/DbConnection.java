package com.db.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbConnection implements IDbConnection {
    private final String connectionString;
    private Connection connection;

    public DbConnection() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("db.properties").getPath();
        String appConfigPath = rootPath + "";
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String login = appProps.getProperty("login");
        String password = appProps.getProperty("password");
        String ip = appProps.getProperty("ip");
        String port = appProps.getProperty("port");
        String database = appProps.getProperty("database");

        connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?user=" + login + "&password=" + password;
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
    public ResultSet select_query(String query) {
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

    public ResultSet query(String query) {
        ResultSet result;
        try {
            Statement pS =  connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            result = pS.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

}

