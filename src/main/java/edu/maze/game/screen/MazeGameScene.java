package edu.maze.game.screen;

import edu.maze.game.bot.BotPlay;
import edu.maze.game.builder.MazeBuilder;
import edu.maze.game.builder.PrimMazeBuilder;
import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.List;

public class MazeGameScene {

    public static int i = 0;

    private static int getDistance(int rows) {
        if (rows == 8)
            return 90;
        else if (rows == 14)
            return 50;
        else
            return 35;
    }

    private static GridPane createMazeGrid(Board board, int rows, int cols) {
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

                // vertical
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

    private static ImageView Image(int distance) throws FileNotFoundException {
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

    private static Button createStartButton() {
        Button button = new Button("I Quit");
        button.setMaxWidth(150);
        button.setFocusTraversable(false);
        return button;
    }

    private static void startButtonAction(Stage stage, Button button) {
        button.setOnAction(event -> {
            stage.setScene(HomePageScene.createHomePageScene(stage));
        });
    }

    private static void sceneAction(Scene scene, ImageView imageView, int distance, Board board) {
        TranslateTransition translateTransition = new TranslateTransition();
        scene.setOnKeyPressed(key -> {
            if (translateTransition.getStatus().equals(Animation.Status.RUNNING)) {
                return;
            }
            double y = imageView.getTranslateY();
            double x = imageView.getTranslateX();

            translateTransition.setDuration(Duration.millis(200));
            translateTransition.setNode(imageView);

            int i = (int) y / (distance + 5);
            int j = (int) x / (distance + 5);

            if (key.getCode() == KeyCode.UP) {
                if (board.isWallRemoved(new Cell(i, j), Board.UP)) {
                    translateTransition.setToY(y - (distance + 5));
                }
            } else if (key.getCode() == KeyCode.DOWN) {
                if (board.isWallRemoved(new Cell(i, j), Board.DOWN)) {
                    translateTransition.setToY(y + (distance + 5));
                }
            } else if (key.getCode() == KeyCode.RIGHT) {
                if (board.isWallRemoved(new Cell(i, j), Board.RIGHT)) {
                    translateTransition.setToX(x + distance + 5);
                }
            } else if (key.getCode() == KeyCode.LEFT) {
                if (board.isWallRemoved(new Cell(i, j), Board.LEFT)) {
                    translateTransition.setToX(x - (distance + 5));
                }
            }
            translateTransition.play();
        });
    }


    public static Scene create(int rows, int cols, Stage stage, int gameMode) throws FileNotFoundException {
        stage.setTitle("Maze Game");

        MazeBuilder builder = new PrimMazeBuilder(rows, cols);
        Board board = builder.build();

        Group group = new Group();

        GridPane gridPane = createMazeGrid(board, rows, cols);
        ImageView imageView = Image(getDistance(rows));

        Scene scene = new Scene(group, 940, 780, Color.rgb(255, 250, 255));

        if (gameMode == HomePageScene.PLAY)
            sceneAction(scene, imageView, getDistance(rows), board);
        else {
            BotPlay play = new BotPlay(rows, cols);
            List<Integer> ls = play.getPath(board);


            for (int i = ls.size() - 1; i >= 0; i--) {
                System.out.println(ls.get(i));
            }
            
        }

        Button button = createStartButton();

        startButtonAction(stage, button);

        Group buttons = new Group();
        buttons.getChildren().

                add(button);
        gridPane.add(buttons, cols + 2, rows / 3, 1, 3);

        group.getChildren().addAll(imageView, gridPane);

        //         stage.setResizable(false);
        scene.getStylesheets().add("style.css");
        return scene;
    }
}
