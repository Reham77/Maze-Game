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
    private static int botMoveIndex;

    private static int getDistance(int rows) {
        if (rows == 8)
            return 90;
        else if (rows == 14)
            return 50;
        else
            return 35;
    }

    private static int getGridIdx(double num, int distance) {
        return ((int) num / (distance + 5));
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

    private static ImageView Image(int distance) {
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

    private static Button createExitButton() {
        Button button = new Button("I Quit");
        button.setMaxWidth(150);
        button.setFocusTraversable(false);
        return button;
    }

    private static void exitButtonAction(Stage stage, Button button) {
        button.setOnAction(event -> {
            stage.setScene(HomePageScene.createHomePageScene(stage));
        });
    }

    private static void moveMouse(TranslateTransition translateTransition, int distance, int direction, Board board) {
        if (translateTransition.getStatus().equals(Animation.Status.RUNNING)) {
            return;
        }
        double y = Double.isNaN(translateTransition.getToY()) ? 0 : translateTransition.getToY();
        double x = Double.isNaN(translateTransition.getToX()) ? 0 : translateTransition.getToX();
        int i = getGridIdx(y, distance);
        int j = getGridIdx(x , distance);

        if (direction == Board.UP && board.isWallRemoved(new Cell(i, j), Board.UP)) {
            translateTransition.setToY(y - (distance + 5));
        } else if (direction == Board.DOWN && board.isWallRemoved(new Cell(i, j), Board.DOWN)) {
            translateTransition.setToY(y + (distance + 5));
        } else if (direction == Board.RIGHT && board.isWallRemoved(new Cell(i, j), Board.RIGHT)) {
            translateTransition.setToX(x + distance + 5);
        } else if (direction == Board.LEFT && board.isWallRemoved(new Cell(i, j), Board.LEFT)) {
            translateTransition.setToX(x - (distance + 5));
        }
        translateTransition.play();
    }

    private static void sceneAction(Scene scene, TranslateTransition imageView, int distance, Board board) {
        scene.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.UP) {
                moveMouse(imageView, distance, Board.UP, board);
            } else if (key.getCode() == KeyCode.DOWN) {
                moveMouse(imageView, distance, Board.DOWN, board);
            } else if (key.getCode() == KeyCode.RIGHT) {
                moveMouse(imageView, distance, Board.RIGHT, board);
            } else if (key.getCode() == KeyCode.LEFT) {
                moveMouse(imageView, distance, Board.LEFT, board);
            }
        });
    }

    public static void moveBot(TranslateTransition translateTransition, int distance, List<Integer> directionsList) {

        botMoveIndex = directionsList.size() - 1;
        translateTransition.setOnFinished(event -> {
            if (botMoveIndex == -1) {
                return;
            }
            double y = translateTransition.getToY();
            double x = translateTransition.getToX();

            if (directionsList.get(botMoveIndex) == Board.UP) {
                translateTransition.setToY(y - (distance + 5));
            } else if (directionsList.get(botMoveIndex) == Board.DOWN) {
                translateTransition.setToY(y + (distance + 5));
            } else if (directionsList.get(botMoveIndex) == Board.RIGHT) {
                translateTransition.setToX(x + distance + 5);
            } else if (directionsList.get(botMoveIndex) == Board.LEFT) {
                translateTransition.setToX(x - (distance + 5));
            }
            --botMoveIndex;
            translateTransition.play();
        });
        translateTransition.setToX(0);
        translateTransition.setToY(0);
        translateTransition.play(); // play empty move to invoke OnFinished Method
    }

    public static Scene create(int rows, int cols, Stage stage, int gameMode) throws FileNotFoundException {
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
