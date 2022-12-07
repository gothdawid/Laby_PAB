package com.ab.aplikacje_biznesowe;

import com.db.api.DbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class HelloApplication extends Application {
    static Scene connect_Scene;
    public static DbConnection connection;

    //logger
    public static Logger logger = Logger.getLogger(HelloApplication.class.getName());


    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Start aplikacji");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connect-view.fxml"));
        logger.info("Ładowanie widoku połączenia");
        stage.setTitle("Panel Administratora");
        connect_Scene = new Scene(fxmlLoader.load(), 300, 420);
        stage.setScene(connect_Scene);
        stage.setResizable(false);
        stage.show();
        logger.info("Wyświetlenie widoku połączenia");
    }

    public static void main(String[] args) {
        launch();
    }
}