package com.ab.aplikacje_biznesowe;

import com.db.api.DbConnection;
import com.db.api.IDbConnection;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {
    public TableView users_table;
    public TableView subjects_table;
    public TableView grades_table;
    public TableView messages_table;
    public TableView groups_table;
    Table users, subjects, grades, messages, groups;


    //initialize
    public void initialize() {
        try {
            users = new Tables_Types.Users("SELECT * FROM users");
            subjects = new Tables_Types.Subjects("SELECT * FROM subjects");
            grades = new Tables_Types.Grades("SELECT * FROM grades");
            messages = new Tables_Types.Messages("SELECT * FROM messages");
            groups = new Tables_Types.Groups("SELECT * FROM groups");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        fillTable(users_table, users);
        fillTable(subjects_table, subjects);
        fillTable(grades_table, grades);
        fillTable(messages_table, messages);
        fillTable(groups_table, groups);
    }

    void fillTable(TableView table, Table data) {
        table.getColumns().clear();
        table.getItems().clear();
        try {
            table.getColumns().addAll(data.getColumns());
            table.setItems(data.getRows());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

