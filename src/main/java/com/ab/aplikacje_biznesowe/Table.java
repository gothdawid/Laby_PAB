package com.ab.aplikacje_biznesowe;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Table implements JTable{
    ResultSet rs;

    public Table(String sql) {
        this.rs = TempApp.connection.query(sql);
    }

    public void printAll() throws SQLException {
       rs.first();
         do {
              toStringRow();
         } while (rs.next());
    }

    public abstract void add();

    public abstract void del();

    public abstract void edit();

    public void goToRowId(int id) throws SQLException {
        boolean found = false;
        rs.first();
        while (!found) {
            if (rs.getInt("id") == id) {
                found = true;
            } else {
                rs.next();
            }
        }
        if (!found) {
            throw new SQLException();
        }
    }

    public abstract void toStringRow();

    public boolean checkId(int id) throws SQLException {
        try {
            goToRowId(id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

