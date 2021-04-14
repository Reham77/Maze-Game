package edu.maze.game;

import edu.maze.game.screen.HomePageScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new HomePageScene().createHomePageScene(primaryStage));
        primaryStage.show();
    }
}
