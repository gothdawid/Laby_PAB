package com.ab.aplikacje_biznesowe;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tables_Types {
    public static class User {
        private int id;
        private String lastName;
        private String firstName;
        private String address;
        private String city;
        private int groupId;

        public User(ResultSet rs) {
            try {
                this.id = rs.getInt("id");
                this.lastName = rs.getString("last_name");
                this.firstName = rs.getString("first_name");
                this.address = rs.getString("address");
                this.city = rs.getString("city");
                this.groupId = rs.getInt("group_id");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public int getId() {
            return id;
        }
        public String getLastName() {
            return lastName;
        }
        public String getFirstName() {
            return firstName;
        }
        public String getAddress() {
            return address;
        }
        public String getCity() {
            return city;
        }
        public int getGroupId() {
            return groupId;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public void setCity(String city) {
            this.city = city;
        }
        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", lastName='" + lastName + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", groupId=" + groupId +
                    '}';
        }

        public void toTableRow() {
            System.out.printf("| %2d | %12s | %10s | %15s | %8s | %2d |%n", id, lastName, firstName, address, city, groupId);
        }
    }
    public static class Grades {
        private int id;
        private int studentId;
        private int teacherId;
        private int subjectId;
        private int grade;

        public Grades(ResultSet rs) {
            try {
                this.id = rs.getInt("id");
                this.studentId = rs.getInt("student_id");
                this.teacherId = rs.getInt("teacher_id");
                this.subjectId = rs.getInt("subject_id");
                this.grade = rs.getInt("grade");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public int getId() {
            return id;
        }
        public int getStudentId() {
            return studentId;
        }
        public int getTeacherId() {
            return teacherId;
        }
        public int getSubjectId() {
            return subjectId;
        }
        public int getGrade() {
            return grade;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }
        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }
        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }
        public void setGrade(int grade) {
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Grades{" +
                    "id=" + id +
                    ", studentId=" + studentId +
                    ", teacherId=" + teacherId +
                    ", subjectId=" + subjectId +
                    ", grade=" + grade +
                    '}';
        }
    }

    public static class Groups {
        private int id;
        private String group_name;

        public Groups(ResultSet rs) {
            try {
                this.id = rs.getInt("id");
                this.group_name = rs.getString("group_name");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public int getId() {
            return id;
        }
        public String getGroup_name() {
            return group_name;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        @Override
        public String toString() {
            return "Groups{" +
                    "id=" + id +
                    ", name='" + group_name + '\'' +
                    '}';
        }

        public void toTableRow() {
            System.out.printf("| %2d | %12s |%n", id, group_name);
        }
    }

    public static class Subjects {
        private int id;
        private String name;
        private int teacher_id;

        public Subjects(ResultSet rs) {
            try {
                this.id = rs.getInt("id");
                this.name = rs.getString("name");
                this.teacher_id = rs.getInt("teacher_id");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public int getTeacher_id() {
            return teacher_id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
        }

        @Override
        public String toString() {
            return "Subjects{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", teacher_id=" + teacher_id +
                    '}';
        }
    }

    public static class Messages {
        private int id;
        private int sender_id;
        private int receiver_id;
        private String message;

        public Messages(ResultSet rs) {
            try {
                this.id = rs.getInt("id");
                this.sender_id = rs.getInt("sender_id");
                this.receiver_id = rs.getInt("receiver_id");
                this.message = rs.getString("message");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public int getId() {
            return id;
        }
        public int getSender_id() {
            return sender_id;
        }
        public int getReceiver_id() {
            return receiver_id;
        }
        public String getMessage() {
            return message;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setSender_id(int sender_id) {
            this.sender_id = sender_id;
        }
        public void setReceiver_id(int receiver_id) {
            this.receiver_id = receiver_id;
        }
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Messages{" +
                    "id=" + id +
                    ", sender_id=" + sender_id +
                    ", receiver_id=" + receiver_id +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

}
