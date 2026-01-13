package edu.icet.task;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Starter extends Application {
    static void main() {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(""))));
        stage.setTitle("Login Form");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("")));
        stage.show();
    }
}
