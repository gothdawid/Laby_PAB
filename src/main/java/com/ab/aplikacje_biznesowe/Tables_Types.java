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

