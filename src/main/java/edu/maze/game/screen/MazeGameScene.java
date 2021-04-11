package edu.maze.game.screen;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.List;

public class MazeGameScene extends MazeGameDrawer {
    private int botMoveIndex;

    protected int getGridIdx(double num, int distance) {
        return ((int) num / (distance + 5));
    }

    private void moveMouse(TranslateTransition translateTransition, int distance, int direction, Board board) {
        if (translateTransition.getStatus().equals(Animation.Status.RUNNING)) {
            return;
        }
        double y = Double.isNaN(translateTransition.getToY()) ? 0 : translateTransition.getToY();
        double x = Double.isNaN(translateTransition.getToX()) ? 0 : translateTransition.getToX();
        int i = getGridIdx(y, distance);
        int j = getGridIdx(x, distance);

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


    @Override
    protected void exitButtonAction(Stage stage, Button button) {
        button.setOnAction(event -> {
            stage.setScene(new HomePageScene().createHomePageScene(stage));
        });
    }

    @Override
    protected void sceneAction(Scene scene, TranslateTransition imageView, int distance, Board board) {
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

    @Override
    protected void moveBot(TranslateTransition translateTransition, int distance, List<Integer> directionsList) {

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
}
