package com.ab.aplikacje_biznesowe;

import com.db.api.IDbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    static Scene connect_Scene;
    public static IDbConnection connection;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connect-view.fxml"));
        stage.setTitle("Panel Administratora");
        connect_Scene = new Scene(fxmlLoader.load(), 300, 420);
        stage.setScene(connect_Scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}