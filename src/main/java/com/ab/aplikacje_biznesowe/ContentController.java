package com.ab.aplikacje_biznesowe;

import java.sql.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.sql.ResultSet;

import static com.ab.aplikacje_biznesowe.HelloApplication.connection;


public class ContentController {
    public Font x3;
    public Color x4;
    public TableView<Tables_Types.Users> users_table;
/*    public TableView<Tables_Types.Grades> grades_table;
    public TableView<Tables_Types.Subjects> subjects_table;
    public TableView<Tables_Types.Messages> messages_table;
    public TableView<Tables_Types.Groups> groups_table;*/


/*    public void parseTable2(ResultSet rs, TableView table){
        table.getItems().clear();
        table.getColumns().clear();

        try {
            while(rs.next()){
                Tables_Types.Users us = new Tables_Types.Users(rs);
                rs.updateString("last_name", "asdasd");
                table.getItems().add(us);
                System.out.println(us.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/

    public void parseTable(ResultSet rs, TableView table) {
        try {
            // We clear the table
            table.getColumns().clear();
            table.getItems().clear();

            // We get the metadata
            ResultSetMetaData rsmd = rs.getMetaData();

            // We get the number of columns
            int columnCount = rsmd.getColumnCount();

            // We create the columns
            for (int i = 1; i <= columnCount; i++) {
                final int j = i;
                TableColumn col = new TableColumn(rsmd.getColumnName(i));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j - 1).toString());
                    }
                });
                table.getColumns().addAll(col);
            }

            // We add the data
            while (rs.next()) {
                // We create a new row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    // We add the data
                    row.add(rs.getString(i));
                }
                // We add the row
                table.getItems().add(row);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        ResultSet users = connection.select_query("SELECT * FROM users;");
        ResultSet subjects = connection.select_query("SELECT * FROM subjects;");
        ResultSet messages = connection.select_query("SELECT * FROM messages;");
        ResultSet groups = connection.select_query("SELECT * FROM groups;");
        ResultSet grades = connection.select_query("SELECT * FROM grades;");

/*        parseTable(subjects, subjects_table);
        parseTable(messages, messages_table);
        parseTable(groups, groups_table);
        parseTable(grades, grades_table);*/
        //parseTable2(users, users_table);

    }
}


