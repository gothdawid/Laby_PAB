package com.ab.aplikacje_biznesowe;

import java.lang.reflect.Type;
import java.sql.*;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.util.ArrayList;

import static com.ab.aplikacje_biznesowe.HelloApplication.connection;


public class ContentController {
    public Font x3;
    public Color x4;
    public TableView<Tables_Types.User> users_table;
    public TableView<Tables_Types.Grades> grades_table;
    public TableView<Tables_Types.Subjects> subjects_table;
    public TableView<Tables_Types.Messages> messages_table;
    public TableView<Tables_Types.Groups> groups_table;


    public void parseTable2(ResultSet rs, TableView table){
        table.getItems().clear();
        table.getColumns().clear();

        try {
            while(rs.next()){
                Tables_Types.User us = new Tables_Types.User(rs);
                table.getItems().add(us);
                System.out.println(us.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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
        ResultSet users = connection.query("SELECT * FROM users;");
        ResultSet subjects = connection.query("SELECT * FROM subjects;");
        ResultSet messages = connection.query("SELECT * FROM messages;");
        ResultSet groups = connection.query("SELECT * FROM groups;");
        ResultSet grades = connection.query("SELECT * FROM grades;");

        parseTable(subjects, subjects_table);
        parseTable(messages, messages_table);
        parseTable(groups, groups_table);
        parseTable(grades, grades_table);
        parseTable2(users, users_table);

    }
}


