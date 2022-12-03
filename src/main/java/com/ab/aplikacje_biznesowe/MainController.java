package com.ab.aplikacje_biznesowe;

import com.db.api.DbConnection;
import com.db.api.IDbConnection;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    public void initialize() {
        try {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


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
}

