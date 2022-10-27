package com.db.api;

import java.sql.Connection;
import java.sql.ResultSet;

public interface IDbConnection {
    Connection getConnection();
    void closeConnection();
    ResultSet select_query(String query);

    ResultSet query(String query);




}
