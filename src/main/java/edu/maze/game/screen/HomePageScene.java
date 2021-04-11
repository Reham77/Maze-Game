package edu.maze.game.screen;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePageScene {
    public final static int PLAY = 0, BOT_PLAY = 1;
    private final String KRUSKAL = "Randomized Kruskal's Algorithm";
    private final String DFS = "Randomized Depth First Search";
    private final String PRIM = "Randomized Prim's Algorithm";
    private int rows = 8, cols = 8;

    private int getGridSize(String selected) {
        if (selected.equals("Hard"))
            return 19;
        if (selected.equals("Medium"))
            return 14;
        return 8;
    }

    private VBox selectLevelComboBox() {
        VBox vBox = new VBox();
        Label label = new Label("Please Select a Level");

        ComboBox level = new ComboBox();
        level.getItems().addAll("Easy", "Medium", "Hard");
        level.setValue("Easy");//set initial state to Easy
        level.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            rows = cols = getGridSize(newValue);
        });
        level.setMaxWidth(230);

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, level);
        return vBox;
    }

    private VBox selectAlgorithmComboBox() {
        VBox vBox = new VBox();
        Label label = new Label("Please Select Maze Generation Algorithm");

        ComboBox algorithm = new ComboBox();
        algorithm.getItems().addAll(DFS, KRUSKAL, PRIM);
        algorithm.setValue(DFS);//set initial state to Easy
        algorithm.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            /// todo
        });
        algorithm.setMaxWidth(230);

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, algorithm);
        return vBox;
    }

    private Button createStartButton(String text, Stage stage, int gameMode) {
        Button button = new Button(text);
        button.setMaxWidth(200);
        button.setOnAction(event -> {
            stage.setScene(new MazeGameScene(rows, cols, gameMode).create(stage));
        });
        return button;
    }


    public Scene createHomePageScene(Stage stage) {
        stage.setTitle("Start Game");
        VBox level = selectLevelComboBox();

        VBox algorithm = selectAlgorithmComboBox();

        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getStyleClass().add("VBox");

        HBox hBox = new HBox();
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMaxWidth(500);
        Button startButton = createStartButton("Play Game", stage, PLAY);
        Button botPlayButton = createStartButton("Bot Play", stage, BOT_PLAY);
        hBox.getChildren().addAll(startButton, botPlayButton);

        vBox2.getChildren().addAll(level, algorithm, hBox);

        Scene scene = new Scene(vBox2, 940, 780);
        scene.getStylesheets().add("style.css");
        return scene;
    }
}
