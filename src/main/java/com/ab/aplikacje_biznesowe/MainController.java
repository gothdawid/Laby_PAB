package com.ab.aplikacje_biznesowe;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {
    public BorderPane pane;
    public static ResultSet users;


    public void about(ActionEvent actionEvent) {
        Stage dialog = new Stage();
        Label label = new Label("Aplikacja do zarządzania szkołą");
        Label label2 = new Label("Autorzy: ");
        Label label3 = new Label("Dawid Studziński");
        Label label4 = new Label("Joanna Wojtasińska");
        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, label2, label3, label4);
        layout.setStyle("-fx-alignment: center");
        dialog.setScene(new Scene(layout, 200, 150));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(HelloApplication.connect_Scene.getWindow());
        dialog.setTitle("O aplikacji");
        dialog.showAndWait();

    }

    //initialize
    public void initialize(){
        loadUsers(null);
    }

    public void loadUsers(ActionEvent actionEvent) {
        int i = 0;
        if (i==0) {users = HelloApplication.connection.executeQuery("SELECT * FROM user");}
        i++;
        ObservableList<User> usersList = FXCollections.observableArrayList();

        TableView<User> table = new TableView<>();
        table.setEditable(true);


        TableColumn<User, Boolean> checkboxColumn = new TableColumn<>("Zaznacz");
        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<User, String> nameColumn = new TableColumn<>("Imię");
        TableColumn<User, String> surnameColumn = new TableColumn<>("Nazwisko");
        TableColumn<User, String> addressColumn = new TableColumn<>("Adres");
        TableColumn<User, String> cityColumn = new TableColumn<>("Miasto");
        TableColumn<User, Integer> groupColumn = new TableColumn<>("Grupa");
        TableColumn<User, String> createdAtColumn = new TableColumn<>("Utworzono");
        TableColumn<User, String> updatedAtColumn = new TableColumn<>("Aktualizacja");
        TableColumn<User, String> passwordColumn = new TableColumn<>("Hasło");

        usersList.clear();
        try {
            while (users.next()) {
                usersList.add(new User(
                                users.getInt("id"),
                                users.getString("first_name"),
                                users.getString("last_name"),
                                users.getString("address"),
                                users.getString("city"),
                                users.getInt("group_id"),
                                users.getString("avatar"),
                                users.getBoolean("isTeacher"),
                                users.getString("createdAt"),
                                users.getString("updatedAt"),
                                users.getString("password"),
                                false
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        checkboxColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<User, Boolean> userBooleanCellDataFeatures) {
                User user = userBooleanCellDataFeatures.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(user.getChecked());

                // Add listener to handler change
                booleanProp.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        user.setChecked(newValue);
                    }
                });
                return booleanProp;
            }
        });
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));
        checkboxColumn.setEditable(true);
        checkboxColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, Boolean> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setChecked(t.getNewValue());
                });


        idColumn.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("id")
        );
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("first_name")
        );
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirst_name(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        surnameColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("last_name")
        );
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLast_name(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        addressColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("address")
        );
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setAddress(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        cityColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("city")
        );
        cityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cityColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCity(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        groupColumn.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("group_id")
        );
        groupColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        groupColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, Integer> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setGroup_id(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        createdAtColumn.setCellFactory(
                TextFieldTableCell.forTableColumn()
        );
        createdAtColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("createdAt")
        );
        createdAtColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCreatedAt(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        updatedAtColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("updatedAt")
        );
        updatedAtColumn.setCellFactory(
                TextFieldTableCell.forTableColumn()
        );
        updatedAtColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUpdatedAt(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        passwordColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("password")
        );
        passwordColumn.setCellFactory(
                TextFieldTableCell.forTableColumn()
        );
        passwordColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPassword(t.getNewValue());
                        //freach userslist
                        for (User user : usersList) {
                            if(user.getChecked()){
                                //print user id
                                System.out.println(user.getId());
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        table.getColumns().addAll(checkboxColumn ,idColumn, nameColumn, surnameColumn, addressColumn, cityColumn, groupColumn, createdAtColumn, updatedAtColumn, passwordColumn);
        table.setItems(usersList);
        //add table after dataTable
        pane.getChildren().clear();
        pane.setCenter(table);
    }

    public void addRow(ActionEvent actionEvent) {

    }
}

