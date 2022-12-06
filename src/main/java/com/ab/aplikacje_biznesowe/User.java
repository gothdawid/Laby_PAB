package com.ab.aplikacje_biznesowe;

import java.sql.SQLException;

public class User {
    private Integer id;
    private String first_name;
    private String last_name;
    private String address;
    private String city;
    private Integer group_id;
    private String Avatar;
    private Boolean isTeacher;
    private String createdAt;
    private String updatedAt;
    private String password;
    private Boolean checked;

    public User(Integer id, String first_name, String last_name, String address, String city, Integer group_id, String Avatar, Boolean isTeacher, String createdAt, String updatedAt, String password, boolean checked) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.city = city;
        this.group_id = group_id;
        this.Avatar = Avatar;
        this.isTeacher = isTeacher;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.password = password;
        this.checked = checked;
    }

    public void goToRowId(int id) throws SQLException {
        boolean found = false;
        MainController.users.first();
        while (!found) {
            if (MainController.users.getInt("id") == id) {
                found = true;
            } else {
                MainController.users.next();
            }
        }
        if (!found) {
            throw new SQLException();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) throws SQLException {
        this.id = id;
        goToRowId(id);
        MainController.users.updateInt("id", id);
        MainController.users.updateRow();
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) throws SQLException {
        this.first_name = first_name;
        goToRowId(id);
        MainController.users.updateString("first_name", first_name);
        MainController.users.updateRow();
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) throws SQLException {
        this.last_name = last_name;
        goToRowId(id);
        MainController.users.updateString("last_name", last_name);
        MainController.users.updateRow();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws SQLException {
        this.address = address;
        goToRowId(id);
        MainController.users.updateString("address", address);
        MainController.users.updateRow();

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws SQLException {
        this.city = city;
        goToRowId(id);
        MainController.users.updateString("city", city);
        MainController.users.updateRow();
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) throws SQLException {
        this.group_id = group_id;
        goToRowId(id);
        MainController.users.updateInt("group_id", group_id);
        MainController.users.updateRow();
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) throws SQLException {
        Avatar = avatar;
        goToRowId(id);
        MainController.users.updateString("Avatar", avatar);
        MainController.users.updateRow();
    }

    public Boolean getTeacher() {
        return isTeacher;
    }

    public void setTeacher(Boolean teacher) throws SQLException {
        isTeacher = teacher;
        goToRowId(id);
        MainController.users.updateBoolean("isTeacher", teacher);
        MainController.users.updateRow();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) throws SQLException {
        this.createdAt = createdAt;
        goToRowId(this.id);
        MainController.users.updateString("createdAt", createdAt);
        MainController.users.updateRow();
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) throws SQLException {
        this.updatedAt = updatedAt;
        goToRowId(this.id);
        MainController.users.updateString("updatedAt", updatedAt);
        MainController.users.updateRow();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws SQLException {
        this.password = password;
        goToRowId(this.id);
        MainController.users.updateString("password", password);
        MainController.users.updateRow();
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

}
