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
    private final String DFS_LABEL = "Randomized Depth First Search";
    private final String KRUSKAL_LABEL = "Randomized Kruskal's Algorithm";
    private final String PRIM_LABEL = "Randomized Prim's Algorithm";

    private Algorithm algorithm = Algorithm.DFS;
    private int rows = 8, cols = 8;

    private int getGridSize(String selected) {
        if (selected.equals("Hard"))
            return 19;
        if (selected.equals("Medium"))
            return 14;
        return 8;
    }

    private Algorithm getAlgorithmType(String selected) {
        if (selected.equals(DFS_LABEL))
            return Algorithm.DFS;
        if (selected.equals(KRUSKAL_LABEL))
            return Algorithm.KRUSKAL;
        return Algorithm.PRIM;
    }

    private VBox selectLevelComboBox() {
        VBox vBox = new VBox();
        Label label = new Label("Select a Difficulty");

        ComboBox level = new ComboBox();
        level.getItems().addAll("Easy", "Medium", "Hard");
        level.setValue("Easy"); //set initial state to Easy
        level.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            rows = cols = getGridSize(newValue);
        });

        level.setMaxWidth(245);

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, level);
        return vBox;
    }

    private VBox selectAlgorithmComboBox() {
        VBox vBox = new VBox();
        Label label = new Label("Select a Maze Generation Algorithm");

        ComboBox algorithmLabel = new ComboBox();
        algorithmLabel.getItems().addAll(DFS_LABEL, KRUSKAL_LABEL, PRIM_LABEL);
        algorithmLabel.setValue(DFS_LABEL); //set initial state to DFS
        algorithmLabel.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            algorithm = getAlgorithmType(newValue);
        });
        algorithmLabel.setMaxWidth(245);

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, algorithmLabel);
        return vBox;
    }

    private Button createStartButton(String text, Stage stage, GameMode gameMode) {
        Button button = new Button(text);
        button.setMaxWidth(200);
        button.setOnAction(event -> {
            stage.setScene(new MazeGameScene(rows, cols, gameMode, algorithm).create(stage));
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
        Button startButton = createStartButton("Play Game", stage, GameMode.PLAY);
        Button botPlayButton = createStartButton("Bot Play", stage, GameMode.BOT_PLAY);
        hBox.getChildren().addAll(startButton, botPlayButton);

        vBox2.getChildren().addAll(level, algorithm, hBox);

        Scene scene = new Scene(vBox2, 940, 780);
        scene.getStylesheets().add("style.css");
        return scene;
    }

    public enum GameMode {
        PLAY, BOT_PLAY;
    }

    public enum Algorithm {
        DFS, KRUSKAL, PRIM
    }
}
