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
    public final static int PLAY = 0, BOTPLAY = 1;
    private int rows = 8, cols = 8;

    private int getGridSize(String selected) {
        if (selected.equals("Hard"))
            return 19;
        if (selected.equals("Medium"))
            return 14;
        return 8;
    }

    public void comboBoxAction(ComboBox comboBox) {
        comboBox.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            rows = cols = getGridSize(newValue);
        });
    }

    public void startButtonAction(Button button, Stage stage, int gameMode) {
        button.setOnAction(event -> {
            stage.setScene(new MazeGameScene(rows, cols, gameMode).create(stage));
        });
    }

    private VBox selectLevelComboBox() {
        VBox vBox = new VBox();
        Label label = new Label("Please Select a level");

        ComboBox level = new ComboBox();
        level.getItems().addAll("Easy", "Medium", "Hard");
        level.setValue("Easy");//set initial state to Easy

        comboBoxAction(level);

        level.setMaxWidth(200);

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, level);

        return vBox;
    }

    private Button createStartButton(String text, Stage stage, int gameMode) {
        Button button = new Button(text);
        button.setMaxWidth(200);
        startButtonAction(button, stage, gameMode);
        return button;
    }


    public Scene createHomePageScene(Stage stage) {
        stage.setTitle("Start Game");
        rows = cols = 8;
        VBox vBox = selectLevelComboBox();
        Button startButton = createStartButton("Play Game", stage, PLAY);
        Button botPlayButton = createStartButton("Bot Play", stage, BOTPLAY);

        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getStyleClass().add("VBox");
        HBox hBox = new HBox();
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMaxWidth(500);
        hBox.getChildren().addAll(startButton, botPlayButton);

        vBox2.getChildren().addAll(vBox, hBox);

        Scene scene = new Scene(vBox2, 940, 780);
        scene.getStylesheets().add("style.css");
        return scene;
    }

}
