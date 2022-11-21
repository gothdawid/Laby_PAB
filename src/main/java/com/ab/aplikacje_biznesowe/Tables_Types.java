package com.ab.aplikacje_biznesowe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tables_Types {

    public static class Users extends Table {
        public Users(String sql) {
            super(sql);
        }

        @Override
        public void add() {
            Scanner scanner = new Scanner(System.in);
            try {
                rs.moveToInsertRow();
                System.out.print("Podaj imie: ");
                rs.updateString("first_name", scanner.nextLine());
                System.out.print("Podaj nazwisko: ");
                rs.updateString("last_name", scanner.nextLine());
                System.out.print("Podaj adres: ");
                rs.updateString("address", scanner.nextLine());
                System.out.print("Podaj miasto: ");
                rs.updateString("city", scanner.nextLine());
                //show groups

                Tables_Types.Groups groups = new Tables_Types.Groups("SELECT * FROM groups");
                groups.printAll();

                System.out.print("Podaj id grupy: ");
                int group_id = scanner.nextInt();
                if (groups.checkId(group_id)) {
                    rs.updateInt("group_id", group_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                rs.insertRow();
                rs.moveToInsertRow();
                System.out.println("Pomyślnie dodano użytkownika!");
            } catch (SQLException e) {
                System.out.println("Nie udało się dodać użytkownika!");
                //e.printStackTrace();
            }

        }

        @Override
        public void del() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                rs.deleteRow();
                System.out.printf("Usunięto użytkownika o id: %d%n", id);
            }
            catch (SQLException e) {
                System.out.println("Nie ma użytkownika o takim id!");
            }
        }

        @Override
        public void edit() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                Groups groups = new Groups("SELECT * FROM groups");

                System.out.println("Wybierz co chcesz edytować:");
                System.out.println("1. Imię");
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
                    groups.printAll();
                    System.out.print("Podaj id grupy: ");
                    int group_id = scanner.nextInt();
                    if (groups.checkId(group_id)) {
                        rs.updateInt("group_id", group_id);
                    } else {
                        System.out.println("Nie ma takiego id!");
                        throw new SQLException();
                    }
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
                    groups.printAll();
                    System.out.print("Podaj id grupy: ");
                    int group_id = scanner.nextInt();
                    if (groups.checkId(group_id)) {
                        rs.updateInt("group_id", group_id);
                    } else {
                        System.out.println("Nie ma takiego id!");
                        throw new SQLException();
                    }
                    rs.updateString("first_name", name);
                    rs.updateString("last_name", surname);
                    rs.updateString("address", address);
                    rs.updateString("city", city);
                    rs.updateInt("group_id", group_id);
                } else {
                    System.out.println("Nie ma takiej opcji!");
                    return;
                }
                rs.updateRow();
                System.out.println("Pomyślnie edytowano użytkownika!");
            }
            catch (SQLException e) {
                System.out.println("Nie ma użytkownika o takim id!");
            }
        }

        @Override
        public void toStringRow(){
            int id, group_id;
            String last_name, first_name, address, city;

            try {
                id = rs.getInt("id");
                group_id = rs.getInt("group_id");
                last_name = rs.getString("last_name");
                first_name = rs.getString("first_name");
                address = rs.getString("address");
                city = rs.getString("city");
                System.out.printf("| %2d | %12s | %12s | %12s | %12s | %2d |%n", id, last_name, first_name, address, city, group_id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Groups extends Table {

        public Groups(String sql) {
            super(sql);
        }

        @Override
        public void add() {
            Scanner scanner = new Scanner(System.in);

            try {
                rs.moveToInsertRow();
                System.out.print("Podaj nazwę grupy: ");
                rs.updateString("group_name", scanner.nextLine());
                rs.insertRow();
                rs.moveToInsertRow();
                System.out.println("Pomyślnie dodano grupę!");
            } catch (SQLException e) {
                System.out.println("Nie udało się dodać grupy!");
                //e.printStackTrace();
            }
        }

        @Override
        public void del() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                rs.deleteRow();
                System.out.printf("Usunięto grupę o id: %d%n", id);
            }
            catch (SQLException e) {
                System.out.println("Nie ma grupy o takim id!");
            }
        }

        @Override
        public void edit() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                System.out.print("Podaj nazwę grupy: ");
                rs.updateString("group_name", scanner.nextLine());
                rs.updateRow();
                System.out.println("Pomyślnie edytowano grupę!");
            }
            catch (SQLException e) {
                System.out.println("Nie ma grupy o takim id!");
            }
        }

        @Override
        public void toStringRow(){
            int id;
            String name;
            try {
                id = rs.getInt("id");
                name = rs.getString("group_name");
                System.out.printf("| %2d | %12s |%n", id, name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Subjects extends Table {
        public Subjects(String sql) {
            super(sql);
        }

        @Override
        public void add() {
            Scanner scanner = new Scanner(System.in);
            try {
                rs.moveToInsertRow();
                System.out.print("Podaj nazwę przedmiotu: ");
                rs.updateString("name", scanner.nextLine());
                Users users = new Users("SELECT * FROM users");
                users.printAll();

                System.out.println("Podaj id nauczyciela: ");
                int teacher_id = scanner.nextInt();
                if (users.checkId(teacher_id)) {
                    rs.updateInt("teacher_id", teacher_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                rs.insertRow();
                rs.moveToInsertRow();
                System.out.println("Pomyślnie dodano przedmiot!");
            } catch (SQLException e) {
                System.out.println("Nie udało się dodać przedmiotu!");
                //e.printStackTrace();
            }
        }

        @Override
        public void del() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                rs.deleteRow();
                System.out.printf("Usunięto przedmiot o id: %d%n", id);
            }
            catch (SQLException e) {
                System.out.println("Nie ma przedmiotu o takim id!");
            }
        }

        @Override
        public void edit() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                System.out.print("Podaj nazwę przedmiotu: ");
                rs.updateString("name", scanner.next());
                Users users = new Users("SELECT * FROM users");
                users.printAll();
                System.out.println("Podaj id nauczyciela: ");
                int teacher_id = scanner.nextInt();
                if (users.checkId(teacher_id)) {
                    rs.updateInt("teacher_id", teacher_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }

                rs.updateRow();
                System.out.println("Pomyślnie edytowano przedmiot!");
            }
            catch (SQLException e) {
                System.out.println("Nie ma przedmiotu o takim id!");
            }
        }

        @Override
        public void toStringRow(){
            int id, teacher_id;
            String name;
            try {
                id = rs.getInt("id");
                name = rs.getString("name");
                teacher_id = rs.getInt("teacher_id");
                System.out.printf("| %2d | %12s | %2d |%n", id, name, teacher_id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Messages extends Table {
        public Messages(String sql) {
            super(sql);
        }

        @Override
        public void add() {
            Scanner scanner = new Scanner(System.in);
            try {
                rs.moveToInsertRow();
                System.out.print("Podaj treść wiadomości: ");
                rs.updateString("message", scanner.nextLine());
                Users users = new Users("SELECT * FROM users");
                users.printAll();
                System.out.println("Podaj id nadawcy: ");
                int sender_id = scanner.nextInt();
                if (users.checkId(sender_id)) {
                    rs.updateInt("sender_id", sender_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                System.out.println("Podaj id odbiorcy: ");
                int receiver_id = scanner.nextInt();
                if (users.checkId(receiver_id)) {
                    rs.updateInt("receiver_id", receiver_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                rs.insertRow();
                rs.moveToInsertRow();
                System.out.println("Pomyślnie dodano wiadomość!");
            } catch (SQLException e) {
                System.out.println("Nie udało się dodać wiadomości!");
                //e.printStackTrace();
            }
        }

        @Override
        public void del() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                rs.deleteRow();
                System.out.printf("Usunięto wiadomość o id: %d%n", id);
            } catch (SQLException e) {
                System.out.println("Nie ma wiadomości o takim id!");
            }
        }

        @Override
        public void edit() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                System.out.print("Podaj treść wiadomości: ");
                rs.updateString("message", scanner.next());
                Users users = new Users("SELECT * FROM users");
                users.printAll();
                System.out.println("Podaj id nadawcy: ");
                int sender_id = scanner.nextInt();
                if (users.checkId(sender_id)) {
                    rs.updateInt("sender_id", sender_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                System.out.println("Podaj id odbiorcy: ");
                int receiver_id = scanner.nextInt();
                if (users.checkId(receiver_id)) {
                    rs.updateInt("receiver_id", receiver_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                rs.updateRow();
                System.out.println("Pomyślnie edytowano wiadomość!");
            } catch (SQLException e) {
                System.out.println("Nie ma wiadomości o takim id!");
            }
        }

        @Override
        public void toStringRow(){
            int id, sender_id, receiver_id;
            String message;
            try {
                id = rs.getInt("id");
                message = rs.getString("message");
                sender_id = rs.getInt("sender_id");
                receiver_id = rs.getInt("receiver_id");
                System.out.printf("| %2d | %12s | %2d | %2d |%n", id, message, sender_id, receiver_id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Grades extends Table {
        public Grades(String sql) {
            super(sql);
        }

        @Override
        public void add() {
            Scanner scanner = new Scanner(System.in);
            try {
                Users users = new Users("SELECT * FROM users");

                rs.moveToInsertRow();
                users.printAll();
                System.out.println("Podaj id nauczyciela: ");
                int teacher_id = scanner.nextInt();
                if (users.checkId(teacher_id)) {
                    rs.updateInt("teacher_id", teacher_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                System.out.println("Podaj id ucznia: ");
                int student_id = scanner.nextInt();
                if (users.checkId(student_id)) {
                    rs.updateInt("student_id", student_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                Subjects subjects = new Subjects("SELECT * FROM subjects");
                subjects.printAll();
                System.out.println("Podaj id przedmiotu: ");
                int subject_id = scanner.nextInt();
                if (subjects.checkId(subject_id)) {
                    rs.updateInt("subject_id", subject_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                System.out.println("Podaj ocenę: ");
                rs.updateInt("grade", scanner.nextInt());
                rs.insertRow();
                rs.moveToInsertRow();
                System.out.println("Pomyślnie dodano ocenę!");
            } catch (SQLException e) {
                System.out.println("Nie udało się dodać oceny!");
                //e.printStackTrace();
            }
        }

        @Override
        public void del() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                rs.deleteRow();
                System.out.printf("Usunięto ocenę o id: %d%n", id);
            } catch (SQLException e) {
                System.out.println("Nie ma oceny o takim id!");
            }
        }

        @Override
        public void edit() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj id: ");
            int id = scanner.nextInt();
            try {
                goToRowId(id);
                Users users = new Users("SELECT * FROM users");
                users.printAll();
                System.out.println("Podaj id nauczyciela: ");
                int teacher_id = scanner.nextInt();
                if (users.checkId(teacher_id)) {
                    rs.updateInt("teacher_id", teacher_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                System.out.println("Podaj id ucznia: ");
                int student_id = scanner.nextInt();
                if (users.checkId(student_id)) {
                    rs.updateInt("student_id", student_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                Subjects subjects = new Subjects("SELECT * FROM subjects");
                subjects.printAll();
                System.out.println("Podaj id przedmiotu: ");
                int subject_id = scanner.nextInt();
                if (subjects.checkId(subject_id)) {
                    rs.updateInt("subject_id", subject_id);
                } else {
                    System.out.println("Nie ma takiego id!");
                    throw new SQLException();
                }
                System.out.println("Podaj ocenę: ");
                rs.updateInt("grade", scanner.nextInt());
                rs.updateRow();
                System.out.println("Pomyślnie edytowano ocenę!");
            } catch (SQLException e) {
                System.out.println("Nie ma oceny o takim id!");
            }
        }

        @Override
        public void toStringRow(){
            int id, teacher_id, student_id, subject_id, grade;
            try {
                id = rs.getInt("id");
                teacher_id = rs.getInt("teacher_id");
                student_id = rs.getInt("student_id");
                subject_id = rs.getInt("subject_id");
                grade = rs.getInt("grade");
                System.out.printf("| %2d | %2d | %2d | %2d | %2d |%n", id, teacher_id, student_id, subject_id, grade);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

