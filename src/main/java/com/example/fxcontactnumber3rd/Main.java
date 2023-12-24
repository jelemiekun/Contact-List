package com.example.fxcontactnumber3rd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Scene1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Scene1Controller scene1Controller = fxmlLoader.getController();
        scene1Controller.setTables();

        stage.getIcons().add(scene1Controller.icon);
        stage.setTitle("Contact Number!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}