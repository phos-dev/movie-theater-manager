package com.movietheater.manager.movietheatermanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("ticket-selection.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Sistema A");
        stage.setScene(scene);
        stage.show();

        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader2 = new FXMLLoader(MainApplication.class.getResource("ticket-selection-two.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load(), 320, 240);
        secondStage.setTitle("Sistema B");
        secondStage.setScene(scene2);
        secondStage.show();
    }
}
