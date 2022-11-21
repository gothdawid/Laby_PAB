package com.ab.aplikacje_biznesowe;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Table{
    ResultSet rs;

    public Table(String sql) {
        this.rs = HelloApplication.connection.select_query(sql);
    }

    public void printAll() throws SQLException {
        columnsNamesToString();
    }

    public void columnsNamesToString() throws SQLException {
        String line = "";
        String columnsNames = "";
        int i ;

        for (i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            columnsNames += String.format("| %-20s", rs.getMetaData().getColumnName(i));
            line += String.format("|%-20s", "---------------------");

        }
        System.out.println(line + "|");
        System.out.println(columnsNames + "|");
        System.out.println(line + "|");
        rs.first();
        do {
            try {
                for (i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.printf("| %-20s", rs.getString(i));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("|");
        } while (rs.next());
        System.out.println(line + "|");

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

    public Object[] getColumns() {
        ArrayList<TableColumn> columns = new ArrayList<>();
        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                columns.add(col);
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
    return columns.toArray();
    }

    public ObservableList getRows() {
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}

