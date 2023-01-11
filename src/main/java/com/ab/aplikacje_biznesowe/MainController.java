package com.ab.aplikacje_biznesowe;

import com.itextpdf.text.DocumentException;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ab.aplikacje_biznesowe.HelloApplication.logger;

public class MainController {
    public BorderPane pane;
    public static ResultSet users;
    public Text logger_out;
    public Button searchButton;
    public TextField searchField;
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
        dialog.setScene(new Scene(layout, 290, 180));
        label.setMaxWidth(240);
        label.setTextAlignment(TextAlignment.JUSTIFY);

        layout.getChildren().addAll(label, label2, label3, label4);
        layout.setStyle("-fx-alignment: center");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(HelloApplication.connect_Scene.getWindow());
        dialog.setTitle("E-Journal");
        dialog.showAndWait();

    }


    public void addRow(ActionEvent actionEvent){
        addRow("","","","","",0,false);
    }

    public void addRow(User user){
        addRow(user.getFirst_name(),user.getLast_name(),user.getAddress(), user.getCity(), user.getPassword(), user.getGroup_id(), user.getTeacher());
    }


    public void addRow(String na, String sur, String addr, String cit, String pass, Integer grid, Boolean iTeach) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(HelloApplication.connect_Scene.getWindow());
        VBox dialogVbox = new VBox(20);
        dialog.setTitle("Wstaw nowego użytkownika");

        dialog.setScene(new Scene(dialogVbox, 380, 340));
        dialog.setResizable(false);
        Label label = new Label("Wstaw użytkownika");
        label.setFont(new Font("Serif", 15));
        TextField name = new TextField();

        HBox dialogHbox1 = new HBox(20);
        name.setPromptText("Imię");
        name.setText(na);
        TextField surname = new TextField();
        surname.setPromptText("Nazwisko");
        surname.setText(sur);
        TextField address = new TextField();
        dialogHbox1.getChildren().addAll(name,surname);

        HBox dialogHbox2 = new HBox(20);
        address.setPromptText("Adres");
        address.setText(addr);
        TextField city = new TextField();
        city.setPromptText("Miasto");
        city.setText(cit);
        TextField group = new TextField();
        dialogHbox2.getChildren().addAll(address,city);

        HBox dialogHbox3 = new HBox(20);
        group.setPromptText("Grupa");
        group.setText(grid.toString());
        TextField password = new TextField();
        password.setPromptText("Hasło");
        password.setText(pass);
        dialogHbox3.getChildren().addAll(group,password);

        CheckBox isTeacher = new CheckBox("Nauczyciel");
        isTeacher.setSelected(iTeach);



        HBox layout = new HBox(10);
        Button button = new Button("Akceptuj");
        Button button2 = new Button("Anuluj");
        layout.getChildren().addAll(button, button2);

        button2.setOnAction(e -> dialog.close());
        button.setOnAction(e -> {
            try {
                users.moveToInsertRow();
                users.updateString("first_name", name.getText());
                users.updateString("last_name", surname.getText());
                users.updateString("address", address.getText());
                users.updateString("city", city.getText());
                users.updateInt("group_id", Integer.parseInt(group.getText()));
                users.updateBoolean("isTeacher", isTeacher.isSelected());
                users.updateString("password", password.getText());
                users.insertRow();
                loadUsers(null);


            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            dialog.close();
        });

        dialogVbox.setAlignment(Pos.CENTER);
        dialogHbox1.setAlignment(Pos.CENTER);
        dialogHbox2.setAlignment(Pos.CENTER);
        dialogHbox3.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);




        dialogVbox.getChildren().addAll(label, dialogHbox1, dialogHbox2, dialogHbox3, isTeacher, layout);

        dialog.showAndWait();
    }
    public void deleteChecked(ActionEvent actionEvent) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(HelloApplication.connect_Scene.getWindow());
        dialog.setTitle("Usuwanie");
        //Label: czy napewno chcesz skasować zaznaczonych użytkowników?
        VBox dialogVbox = new VBox(20);
        dialog.setScene(new Scene(dialogVbox, 420,400));
        Label label = new Label("Czy napewno chcesz skasować zaznaczonych użytkowników?");
        label.setFont(new Font("Serif", 13));

        Insets insets = new Insets(10,20,10,20);
        dialogVbox.setPadding(insets);

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
//        if (list.getItems().size() == 0){
//            ObservableList<User> selectedUsers = table.getSelectionModel().getSelectedItems();
//            logger.info(String.valueOf(selectedUsers.size()));
//            for (User user : selectedUsers) {
//                items.add(user.getId() +": " + user.getFirst_name() + " " + user.getLast_name());
//            }
//        }

        ButtonBar buttonBar = new ButtonBar();
        Button button = new Button("Usuń");
        Button button2 = new Button("Anuluj");
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

        pane.setOnKeyPressed(event -> {
            if(event.isControlDown() && event.getCode().toString().equals("I")){
                addRow("", "", "", "", "", 0, false);
            }
            if(event.isControlDown() && event.getCode().toString().equals("D")){
                deleteChecked(null);
            }
            if(event.isControlDown() && event.getCode().toString().equals("C")){
                addRow(table.getSelectionModel().getSelectedItem());
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
        //searchField on enter
        searchField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                searchButton.fire();
            }
        });
        searchButton.addEventHandler(ActionEvent.ACTION, event -> {
            if(!event.getEventType().equals(ActionEvent.ACTION)){
                return;
            }
            try {
                usersList.clear();
                users.beforeFirst();
                while (users.next()) {
                    if (users.getString("first_name").toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            users.getString("last_name").toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            users.getString("city").toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            users.getString("address").toLowerCase().contains(searchField.getText().toLowerCase())

                    ) {
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
                }
                RefreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

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

        //contex menu
        table.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem("Usuń");
            MenuItem copyItem = new MenuItem("Kopiuj");
            MenuItem addItem = new MenuItem("Dodaj");
            deleteItem.setOnAction(event -> {
                deleteChecked(null);
            });
            copyItem.setOnAction(event -> {
                addRow(row.getItem());
                System.out.println("Kopiuj");
            });
            addItem.setOnAction(event -> {
                addRow("", "", "", "", "", 0, false);
            });
            contextMenu.getItems().addAll(addItem, copyItem, deleteItem);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );
            return row ;
        });


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
        DownloadUsers();
    }


    public void copyFromMenu(ActionEvent actionEvent) {
        User us = table.getSelectionModel().getSelectedItem();
        if (us == null) {
            return;
        }
        addRow(us);
    }

    public void inserFromMenu(ActionEvent actionEvent) {
        addRow("","","","","",0,false);
    }

    public void deleteFromMenu(ActionEvent actionEvent) {
        deleteChecked(null);
    }

    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void pdfCreator(ActionEvent actionEvent) {
        try {
            logger.info("Creating PDF");
            PdfCreator pdfCreator = new PdfCreator();
            pdfCreator.createPdf(usersList);
            logger.info("PDF created");
        } catch (Exception e) {
            logger.info("Error while creating pdf");
        }
    }
}

