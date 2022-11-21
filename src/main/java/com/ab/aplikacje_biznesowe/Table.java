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
import java.util.HashMap;

public abstract class Table{
    ResultSet rs;

    public Table(String sql) {
        this.rs = HelloApplication.connection.select_query(sql);
    }

    public void add(HashMap<String, String> data) throws SQLException {
        rs.moveToInsertRow();
        for (String key : data.keySet()) {
            rs.updateString(key, data.get(key));
        }
        rs.insertRow();
        rs.moveToInsertRow();
    }

    public void del(int id) throws SQLException {
        goToRowId(id);
        rs.deleteRow();

    }

    public void edit(HashMap<String, String> data, int id) throws SQLException {
        goToRowId(id);
        for (String key : data.keySet()) {
            rs.updateString(key, data.get(key));
        }
        rs.updateRow();
    }


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

    public ObservableList<ObservableList<String>> getRows() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
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

