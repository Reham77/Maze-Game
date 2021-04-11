package edu.maze.game.builder;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePageScene {
    private static int rows = 8, cols = 8;

    private static int getGridSize(String selected) {
        if (selected.equals("Hard"))
            return 19;
        if (selected.equals("Medium"))
            return 14;
        return 8;
    }

    public static void comboBoxAction(ComboBox comboBox) {
        comboBox.valueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            rows = cols = getGridSize(newValue);
        });
    }

    public static void startButtonAction(Button button, Stage stage) {
        button.setOnAction(event -> {
            // do nothing
        });
    }

    private static VBox selectLevelComboBox() {
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

    private static Button createStartButton(Stage stage) {
        Button button = new Button("Start");
        button.setMaxWidth(200);
        startButtonAction(button, stage);
        return button;
    }

    public static Scene createHomePageScene(Stage stage) {
        stage.setTitle("Start Game");
        rows = cols = 8;
        VBox vBox = selectLevelComboBox();
        Button startButton = createStartButton(stage);

        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getStyleClass().add("VBox");
        vBox2.getChildren().addAll(vBox, startButton);

        Scene scene = new Scene(vBox2, 940, 780);
        scene.getStylesheets().add("style.css");
        return scene;
    }

}
