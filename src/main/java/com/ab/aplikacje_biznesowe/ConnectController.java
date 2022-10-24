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
        String connectionString = "jdbc:mysql://" + adres.getText() + ":" + port.getText() + "/" + db.getText() + "?user=" + login.getText() + "&password=" + password.getText();
        System.out.println(connectionString);
        try {
            connection = new DbConnection(login.getText(), password.getText(), "mysql", adres.getText(), db.getText(), port.getText());
            connection.getConnection();
            connectionResults.setText("Połączono!");
            //wait(2000);
            Scene newScene = new Scene(FXMLLoader.load(HelloApplication.class.getResource("content-view.fxml")));
            Stage stage = (Stage) connectionResults.getScene().getWindow();
            stage.setScene(newScene);
        } catch (Exception e) {
            connectionResults.setText("Błąd połączenia!");
            System.out.println(e.getMessage());
        }

    }
}