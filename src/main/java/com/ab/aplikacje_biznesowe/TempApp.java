package com.ab.aplikacje_biznesowe;

import com.db.api.DbConnection;
import com.db.api.IDbConnection;
import javafx.scene.Scene;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TempApp {
    static Scene connect_Scene;
    public static IDbConnection connection;

    public static void main(String[] args) throws SQLException {
        connection = new DbConnection();
        connection.getConnection();
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while(true) {
            Tables_Types.Users users = new Tables_Types.Users("SELECT * FROM users");
            Tables_Types.Subjects subjects = new Tables_Types.Subjects("SELECT * FROM subjects");
            Tables_Types.Grades grades = new Tables_Types.Grades("SELECT * FROM grades");
            Tables_Types.Messages messages = new Tables_Types.Messages("SELECT * FROM messages");
            Tables_Types.Groups groups = new Tables_Types.Groups("SELECT * FROM groups");

            //Table allTAbles = new Table();



            boolean returnToMenu = false;
            Table table = null;

            System.out.println("Wybierz tabelę:");
            System.out.println("1. Użytkownicy");
            System.out.println("2. Zajęcia");
            System.out.println("3. Oceny");
            System.out.println("4. Grupy");
            System.out.println("5. Wiadomości");
            System.out.println("6. Wyjście");
            System.out.print("Wybierz: ");
            choice = scanner.nextInt();

            if (choice < 1 || choice > 6) {
                System.out.println("Nie ma takiej opcji!");
                continue;
            }

            switch (choice) {
                case 1:
                    table = users;
                    break;
                case 2:
                    table = subjects;
                    break;
                case 3:
                    table = grades;
                    break;
                case 4:
                    table = groups;
                    break;
                case 5:
                    table = messages;
                    break;
                case 6:
                    System.exit(0);
            }

            while (true) {
                if(returnToMenu) {
                    break;
                }
                int choice2 = 0;
                System.out.println("Wybierz akcję:");
                System.out.println("1. Dodaj");
                System.out.println("2. Usuń");
                System.out.println("3. Edytuj");
                System.out.println("4. Wyświetl");
                System.out.println("5. Powrót");
                System.out.print("Wybierz: ");
                choice2 = scanner.nextInt();

                if (choice2 < 1 || choice2 > 5) {
                    System.out.println("Nie ma takiej opcji!");
                    continue;
                }

                switch (choice2) {
                    case 1:
                        table.add();
                        break;
                    case 2:
                        table.del();
                        break;
                    case 3:
                        table.edit();
                        break;
                    case 4:
                        table.printAll();
                        break;
                    case 5:
                        returnToMenu = true;
                        break;
                }
            }
        }
    }
}