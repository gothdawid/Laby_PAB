package com.ab.aplikacje_biznesowe;

import com.db.api.DbConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.w3c.dom.events.Event;

import java.util.Objects;

import static com.ab.aplikacje_biznesowe.HelloApplication.connect_Scene;
import static com.ab.aplikacje_biznesowe.HelloApplication.connection;


public class ConnectController {
    public PasswordField password;
    public TextField login, db, port, adres;
    @FXML
    private Label connectionResults;

    @FXML
    public void initialize() {
        //key handler on scene
        login.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                password.requestFocus();
            }
        });
        password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onConnectButtonClick();
            }
        });
    }

    @FXML
    protected void onConnectButtonClick() {
        try {
            connection = new DbConnection(adres.getText(), port.getText(),  db.getText(), login.getText(), password.getText(), "mysql");
            connection.getConnection();
            connectionResults.setText("Połączono!");

            Scene newScene = new Scene(FXMLLoader.load(HelloApplication.class.getResource("main-view.fxml")));
            Stage stage = (Stage) connectionResults.getScene().getWindow();
            stage.setScene(newScene);
        } catch (Exception e) {
            connectionResults.setText("Błąd połączenia!");
            System.out.println(e.getMessage());
        }
    }
}