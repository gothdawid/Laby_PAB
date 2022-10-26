package com.ab.aplikacje_biznesowe;

public class Tables_Types {
    class User {
        private int id;
        private String lastName;
        private String firstName;
        private String address;
        private String city;
        private int groupId;

        public User(int id, String lastName, String firstName, String address, String city, int groupId) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.address = address;
            this.city = city;
            this.groupId = groupId;
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
    }

    class Grades {
        private int id;
        private int studentId;
        private int teacherId;
        private int subjectId;
        private int grade;

        public Grades(int id, int studentId, int teacherId, int subjectId, int grade) {
            this.id = id;
            this.studentId = studentId;
            this.teacherId = teacherId;
            this.subjectId = subjectId;
            this.grade = grade;
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

    class Groups {
        private int id;
        private String group_name;

        public Groups(int id, String group_name) {
            this.id = id;
            this.group_name = group_name;
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
    }

    class Subjects {
        private int id;
        private String name;
        private int teacher_id;

        public Subjects(int id, String name, int teacher_id) {
            this.id = id;
            this.name = name;
            this.teacher_id = teacher_id;
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

    class Messages {
        private int id;
        private int sender_id;
        private int receiver_id;
        private String message;

        public Messages(int id, int sender_id, int receiver_id, String message) {
            this.id = id;
            this.sender_id = sender_id;
            this.receiver_id = receiver_id;
            this.message = message;
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
