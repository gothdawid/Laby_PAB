package com.ab.aplikacje_biznesowe;

import com.db.api.IDbConnection;
import com.db.api.DbConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.events.Event;
import static com.ab.aplikacje_biznesowe.HelloApplication.connection;


public class ConnectController {
    public PasswordField password;
    public TextField login, db, port, adres;
    @FXML
    private Label connectionResults;

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