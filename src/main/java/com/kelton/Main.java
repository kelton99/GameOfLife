package com.kelton;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainView view = new MainView();
        stage.setScene(new Scene(view, 1000, 700));
        stage.show();
        view.draw();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
