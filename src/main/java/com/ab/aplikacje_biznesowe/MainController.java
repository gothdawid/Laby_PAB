package com.ab.aplikacje_biznesowe;

import com.db.api.DbConnection;
import com.db.api.IDbConnection;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {
    public TableView<Table> users_table, subjects_table, grades_table, messages_table, groups_table, room_table, schedule_table;
    Table users, subjects, grades, messages, groups, room, schedule;


    //initialize
    public void initialize() {
        try {
            users = new Tables_Types.Users("SELECT * FROM user");
            subjects = new Tables_Types.Subjects("SELECT * FROM subject");
            grades = new Tables_Types.Grades("SELECT * FROM grade");
            messages = new Tables_Types.Messages("SELECT * FROM message");
            groups = new Tables_Types.Groups("SELECT * FROM `group`");
            room = new Tables_Types.Room("SELECT * FROM room");
            schedule = new Tables_Types.Schedule("SELECT * FROM schedule");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        fillTable(users_table, users);
        fillTable(subjects_table, subjects);
        fillTable(grades_table, grades);
        fillTable(messages_table, messages);
        fillTable(groups_table, groups);
        fillTable(room_table, room);
        fillTable(schedule_table, schedule);
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

