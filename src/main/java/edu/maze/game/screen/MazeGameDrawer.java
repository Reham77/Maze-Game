package edu.maze.game.screen;

import edu.maze.game.bot.BotPlay;
import edu.maze.game.builder.MazeBuilder;
import edu.maze.game.builder.PrimMazeBuilder;
import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public abstract class MazeGameDrawer {
    private int getDistance(int rows) {
        if (rows == 8)
            return 90;
        else if (rows == 14)
            return 50;
        else
            return 35;
    }

    private GridPane createMazeGrid(Board board, int rows, int cols) {
        int distance = getDistance(rows);

        GridPane gridPane = new GridPane();

        for (int x = 0; x <= rows; x++) {
            for (int y = 0; y <= cols; y++) {
                Group group = new Group();
                Line horizontal = new Line(0, 0, distance, 0);
                if (board.isWallRemoved(new Cell(x, y), Board.UP) || y == rows) {
                    horizontal.setStyle("-fx-stroke: #ff0000 ; -fx-stroke-width: 5");
                    horizontal.setOpacity(0);
                } else {
                    horizontal.setStyle("-fx-stroke: #0d3b12 ; -fx-stroke-width: 5");
                }

                Line vertical = new Line(0, 0, 0, distance);
                if (board.isWallRemoved(new Cell(x, y), Board.LEFT) || x == cols) {
                    vertical.setStyle("-fx-stroke: #ff0000 ; -fx-stroke-width: 5");
                    vertical.setOpacity(0);
                } else {
                    vertical.setStyle("-fx-stroke: #022d26 ; -fx-stroke-width: 5");
                }

                group.getChildren().addAll(vertical, horizontal);
                gridPane.add(group, y, x);
            }
        }
        return gridPane;
    }

    private ImageView Image(int distance) {
        Image image = new Image("file:src/main/resources/mouse.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setX((distance / 10) + 5);
        imageView.setY((distance / 10) + 5);
        imageView.setFitHeight(distance + 5);
        imageView.setFitWidth(distance + 5);
        imageView.setPreserveRatio(true);
        imageView.setFocusTraversable(true);

        return imageView;
    }

    private Button createExitButton() {
        Button button = new Button("I Quit");
        button.setMaxWidth(150);
        button.setFocusTraversable(false);
        return button;
    }

    protected abstract void exitButtonAction(Stage stage, Button button);

    protected abstract void sceneAction(Scene scene, TranslateTransition imageView, int distance, Board board);

    protected abstract void moveBot(TranslateTransition translateTransition, int distance,
                                    List<Integer> directionsList);

    public Scene create(int rows, int cols, Stage stage, int gameMode) {
        stage.setTitle("Maze Game");

        MazeBuilder builder = new PrimMazeBuilder(rows, cols);
        Board board = builder.build();

        Group group = new Group();

        GridPane gridPane = createMazeGrid(board, rows, cols);
        ImageView imageView = Image(getDistance(rows));
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), imageView);

        Scene scene = new Scene(group, 940, 780, Color.rgb(255, 250, 255));

        if (gameMode == HomePageScene.PLAY)
            sceneAction(scene, translateTransition, getDistance(rows), board);
        else {
            BotPlay play = new BotPlay(rows, cols);
            List<Integer> directionsList = play.getPath(board);
            moveBot(translateTransition, getDistance(rows), directionsList);
        }

        Button button = createExitButton();
        exitButtonAction(stage, button);

        Group buttons = new Group();
        buttons.getChildren().add(button);
        gridPane.add(buttons, cols + 2, rows / 3, 1, 3);

        group.getChildren().addAll(imageView, gridPane);
        //         stage.setResizable(false);
        scene.getStylesheets().add("style.css");
        return scene;
    }
}
