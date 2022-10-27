package com.ab.aplikacje_biznesowe;

import com.db.api.DbConnection;
import com.db.api.IDbConnection;
import javafx.scene.Scene;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TempApp {
    static Scene connect_Scene;
    public static IDbConnection connection;

    public static void main(String[] args) throws SQLException {
        connection = new DbConnection();
        connection.getConnection();

        while(true) {
            System.out.print("Podaj akcję: ");
            ResultSet rs = connection.query("SELECT * FROM users");

            Scanner scanner = new Scanner(System.in);
            String action = scanner.nextLine();
            if (action.equals("exit")) {
                break;
            } else if(action.equals("show")) {
                showUsers(rs);
            } else if(action.equals("add")) {
                addUser(rs, scanner);
            } else if(action.equals("del")) {
                delUser(rs, scanner);
            } else if(action.equals("edit")) {
                editUser(connection, rs, scanner);
            } else {
                System.out.println("show - wyświetl tabelę");
                System.out.println("add - dodaj");
                System.out.println("del - usuń");
                System.out.println("edit - edytuj");
                System.out.println("exit - wyjście");
            }
        }
    }

    private static void showUsers(ResultSet rs) throws SQLException {
        while (rs.next()) {
            new Tables_Types.User(rs).toTableRow();
        }
    }

    private static boolean editUser(IDbConnection con, ResultSet rs, Scanner scanner) throws SQLException {
        System.out.print("Podaj id: ");
        int id = scanner.nextInt();
        try {
            getRowById(rs, id);
        }
        catch (SQLException e) {
            System.out.println("Nie ma takiego id!");
            return false;
        }

        System.out.println("Co chcesz edytyować?");
        System.out.println("1. Imie");
        System.out.println("2. Nazwisko");
        System.out.println("3. Adres");
        System.out.println("4. Miasto");
        System.out.println("5. Id grupy");
        System.out.println("6. Wszystko");
        System.out.println("7. Anuluj");
        int edit = scanner.nextInt();
        if (edit == 1) {
            System.out.print("Podaj imie: ");
            String name = scanner.next();
            rs.updateString("first_name", name);
        } else if (edit == 2) {
            System.out.print("Podaj nazwisko: ");
            String surname = scanner.next();
            rs.updateString("last_name", surname);
        } else if (edit == 3) {
            System.out.print("Podaj adres: ");
            String address = scanner.next();
            rs.updateString("address", address);
        } else if (edit == 4) {
            System.out.print("Podaj miasto: ");
            String city = scanner.next();
            rs.updateString("city", city);
        } else if (edit == 5) {
            System.out.print("Podaj id grupy: ");
            int group_id = scanner.nextInt();
            rs.updateInt("group_id", group_id);
        } else if (edit == 6) {
            System.out.print("Podaj imie: ");
            String name = scanner.next();
            System.out.print("Podaj nazwisko: ");
            String surname = scanner.next();
            System.out.print("Podaj adres: ");
            String address = scanner.next();
            System.out.print("Podaj miasto: ");
            String city = scanner.next();
            //pobieranie listy grup
            System.out.println("- Grupy w bazie danych -");
            ResultSet rs2 = con.query("SELECT * FROM groups");
            while (rs2.next()) {
                new Tables_Types.Groups(rs2).toTableRow();
            }
            System.out.print("Wybierz id grupy: ");
            int group_id = scanner.nextInt();


            rs.updateString("first_name", name);
            rs.updateString("last_name", surname);
            rs.updateString("address", address);
            rs.updateString("city", city);
            rs.updateInt("group_id", group_id);
        } else if (edit == 7) {
            return true;
        } else {
            System.out.println("Nie ma takiej opcji!");
        }
        rs.updateRow();
        return false;
    }

    private static void delUser(ResultSet rs, Scanner scanner) throws SQLException {
        System.out.print("Podaj id: ");
        int id = scanner.nextInt();
        try {
            getRowById(rs, id);
            rs.deleteRow();
            System.out.println("Usunięto!");
        }
        catch (SQLException e) {
            System.out.println("Nie ma takiego id!");
        }

    }

    public static void getRowById(ResultSet rs, int id) throws SQLException {
        boolean found = false;
        while (rs.next()) {
            if (rs.getInt("id") == id) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new SQLException();
        }
    }

    private static void addUser(ResultSet rs, Scanner scanner) throws SQLException {
        System.out.print("Podaj imie: ");
        String name = scanner.next();
        System.out.print("Podaj nazwisko: ");
        String surname = scanner.next();
        System.out.print("Podaj adres: ");
        String address = scanner.next();
        System.out.print("Podaj miasto: ");
        String city = scanner.next();
        System.out.print("Podaj id grupy: ");
        int group_id = scanner.nextInt();

        rs.moveToInsertRow();
        rs.updateString("first_name", name);
        rs.updateString("last_name", surname);
        rs.updateString("address", address);
        rs.updateString("city", city);
        rs.updateInt("group_id", group_id);
        rs.insertRow();
        rs.moveToCurrentRow();
    }
}