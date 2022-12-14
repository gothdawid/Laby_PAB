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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ab.aplikacje_biznesowe.HelloApplication.logger;

public class MainController {
    public BorderPane pane;
    public static ResultSet users;
    public Text logger_out;
    ObservableList<User> usersList = FXCollections.observableArrayList();
    TableView<User> table = new TableView<>();

    public static final Font ITALIC_FONT =
            Font.font(
                    "Serif",
                    FontPosture.ITALIC,
                    Font.getDefault().getSize()
            );

    public void about(ActionEvent actionEvent) {
        Stage dialog = new Stage();
        Label label = new Label("E-Journal to dziennik elektroniczny, który służy do " +
                "rejestru przebiegu nauczania. Aplikacja z pozniomu administratora" +
                " pozwala na wstawianie, edytowanie oraz usuwanie uczniów i naczycieli.");
        Label label2 = new Label("Autorzy: ");
        label2.setFont(ITALIC_FONT);
        Label label3 = new Label("Dawid Studziński");
        label3.setFont(ITALIC_FONT);
        Label label4 = new Label("Joanna Wojtasińska");
        label4.setFont(ITALIC_FONT);
        VBox layout = new VBox(10);

        label.setWrapText(true);
        dialog.setScene(new Scene(layout, 250, 180));
        label.setMaxWidth(240);
        label.setTextAlignment(TextAlignment.JUSTIFY);

        layout.getChildren().addAll(label, label2, label3, label4);
        layout.setStyle("-fx-alignment: center");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(HelloApplication.connect_Scene.getWindow());
        dialog.setTitle("E-Journal");
        dialog.showAndWait();

    }

    public void addRow(ActionEvent actionEvent) {
        //open new window for add new user
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(HelloApplication.connect_Scene.getWindow());
        dialog.setTitle("Add new user");

        VBox dialogVbox = new VBox(20);
        dialog.setScene(new Scene(dialogVbox, 600, 500));
        Label label = new Label("Add new user");
        label.setFont(new Font("Arial", 20));
        TextField name = new TextField();
        name.setPromptText("Name");
        TextField surname = new TextField();
        surname.setPromptText("Surname");
        TextField address = new TextField();
        address.setPromptText("Address");
        TextField city = new TextField();
        city.setPromptText("City");
        TextField group = new TextField();
        group.setPromptText("Group");
        TextField password = new TextField();
        password.setPromptText("Password");
        CheckBox isTeacher = new CheckBox("Is teacher");




        Button button = new Button("Add");
        Button button2 = new Button("Cancel");
        button2.setOnAction(e -> dialog.close());
        button.setOnAction(e -> {
            try {
                users.moveToInsertRow();
                users.updateString("first_name", name.getText());
                users.updateString("last_name", surname.getText());
                users.updateString("address", address.getText());
                users.updateString("city", city.getText());
                users.updateInt("group_id", Integer.parseInt(group.getText()));
                users.updateBoolean("isTeacher", false);
                users.updateString("password", password.getText());
                users.insertRow();
                loadUsers(null);


            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            dialog.close();
        });

        dialogVbox.getChildren().addAll(label, name, surname, address, city, group, password, isTeacher, button, button2);

        dialog.showAndWait();
    }
    public void deleteChecked(ActionEvent actionEvent) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(HelloApplication.connect_Scene.getWindow());
        dialog.setTitle("Delete users");
        //Label: czy napewno chcesz skasować zaznaczonych użytkowników?
        VBox dialogVbox = new VBox(20);
        dialog.setScene(new Scene(dialogVbox, 300,400));
        Label label = new Label("Czy napewno chcesz skasować zaznaczonych użytkowników?");
        //wyświetl listę zaznaczonych użytkowników
        ListView<String> list = new ListView<String>();
        list.setPrefSize(300, 300);
        ObservableList<String> items =FXCollections.observableArrayList ();
        for (User user : usersList) {
            if(user.getChecked()){
                items.add(user.getId() +": " + user.getFirst_name() + " " + user.getLast_name());
            }
        }
        list.setItems(items);
        if (items.size() == 0){
            //get selected users from table
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            ObservableList<User> selectedUsers = table.getSelectionModel().getSelectedItems();
            for (User user : selectedUsers) {
                items.add(user.getId() +": " + user.getFirst_name() + " " + user.getLast_name());
            }
        }

        ButtonBar buttonBar = new ButtonBar();
        Button button = new Button("Delete");
        Button button2 = new Button("Cancel");
        buttonBar.getButtons().addAll(button, button2);

        button2.setOnAction(e -> dialog.close());
        button.setOnAction(e -> {
            try {
                for (User user : usersList) {
                    if(user.getChecked()){
                        users.beforeFirst();
                        while (users.next()) {
                            if (users.getInt("id") == user.getId()) {
                                users.deleteRow();
                                logger.info("Użytkownik " + user.getFirst_name() + " " + user.getLast_name() + "od id:" + user.getId() +" został usunięty.");
                            }
                        }
                    }
                }
                DownloadUsers();
                loadUsers(null);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            dialog.close();
        });

        dialogVbox.getChildren().addAll(label, list, buttonBar);
        dialog.showAndWait();
    }

    public void initialize(){
        //add key handler
        //if ctrl + i insert new row
        //if ctrl + d delete row

        pane.setOnKeyPressed(event -> {
            if(event.isControlDown() && event.getCode().toString().equals("I")){
                addRow(null);
            }
            if(event.isControlDown() && event.getCode().toString().equals("D")){
                deleteChecked(null);
            }
        });


        logger.addHandler(new MyHandler(logger_out));
        logger.info("Ładowanie widoku głównego");
        loadUsers(null);
        logger.info("Załadowano użytkowników");
    }

    public void RefreshTable() throws SQLException {
        logger.info("Odświeżanie tabeli");
        //table.getItems().clear();
        table.setItems(usersList);
        pane.getChildren().clear();
        pane.setCenter(table);
        logger.info("Odświeżono tabelę");
    }
    public void DownloadUsers() {
        try {
            logger.info("Pobieranie użytkowników");
            usersList.clear();
            users.beforeFirst();
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
            logger.info("Pobrano "+ usersList.size()+" użytkowników");
            RefreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadUsers(ActionEvent actionEvent) {
        int i = 0;
        users = HelloApplication.connection.executeQuery("SELECT * FROM user");

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
        TableColumn<User, Boolean> isTeacherColumn = new TableColumn<>("Nauczyciel");

        checkboxColumn.setCellValueFactory(userBooleanCellDataFeatures -> {
            User user = userBooleanCellDataFeatures.getValue();

            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(user.getChecked());
            booleanProp.addListener((observable, oldValue, newValue) -> user.setChecked(newValue));
            return booleanProp;
        });
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));
        checkboxColumn.setEditable(true);
        checkboxColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, Boolean> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setChecked(t.getNewValue());
                });


        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameColumn.setCellValueFactory( new PropertyValueFactory<User, String>("first_name"));
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
        surnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("last_name"));
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
        addressColumn.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
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
        cityColumn.setCellValueFactory(new PropertyValueFactory<User, String>("city"));
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

        groupColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("group_id"));
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

        createdAtColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<User, String>("createdAt"));
        createdAtColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCreatedAt(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        updatedAtColumn.setCellValueFactory(new PropertyValueFactory<User, String>("updatedAt"));
        updatedAtColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        updatedAtColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUpdatedAt(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
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
        isTeacherColumn.setCellValueFactory(userBooleanCellDataFeatures -> {
            User user = userBooleanCellDataFeatures.getValue();

            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(user.getTeacher());
            booleanProp.addListener((observable, oldValue, newValue) -> {
                try {
                    user.setTeacher(newValue);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return booleanProp;
        });
        isTeacherColumn.setCellFactory(CheckBoxTableCell.forTableColumn(isTeacherColumn));
        isTeacherColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<User, Boolean> t) -> {
                    try {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTeacher(t.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );


        table.getColumns().addAll(checkboxColumn ,idColumn, nameColumn, surnameColumn, addressColumn, cityColumn, groupColumn, createdAtColumn, updatedAtColumn, passwordColumn, isTeacherColumn);

        DownloadUsers();
    }


    public void clearChecked(ActionEvent actionEvent) {
        for (User user : usersList) {
            user.setChecked(false);
        }
        //RefreshTable();
        DownloadUsers();
    }


}

