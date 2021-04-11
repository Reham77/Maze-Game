package edu.maze.game.screen;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import edu.maze.game.screen.HomePageScene.Algorithm;
import edu.maze.game.screen.HomePageScene.GameMode;
import javafx.animation.Animation;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.List;

public class MazeGameScene extends MazeGameDrawer {
    private int botMoveIndex;

    public MazeGameScene(int rows, int cols, GameMode gameMode, Algorithm algorithm) {
        super(rows, cols, gameMode, algorithm);
    }

    protected int getGridIdx(double num) {
        return ((int) num / (distance + 5));
    }

    private void moveMouse(int direction, Stage stage) {
        if (translateTransition.getStatus().equals(Animation.Status.RUNNING)) {
            return;
        }
        double y = Double.isNaN(translateTransition.getToY()) ? 0 : translateTransition.getToY();
        double x = Double.isNaN(translateTransition.getToX()) ? 0 : translateTransition.getToX();
        int i = getGridIdx(y);
        int j = getGridIdx(x);

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

        if (getGridIdx(translateTransition.getToX()) == rows - 1 && getGridIdx(translateTransition.getToY()) == cols - 1) {
            createAlertBox(stage);
        }
    }

    @Override
    protected void sceneOnPressAction(Scene scene, Stage stage) {
        scene.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.UP) {
                moveMouse(Board.UP, stage);
            } else if (key.getCode() == KeyCode.DOWN) {
                moveMouse(Board.DOWN, stage);
            } else if (key.getCode() == KeyCode.RIGHT) {
                moveMouse(Board.RIGHT, stage);
            } else if (key.getCode() == KeyCode.LEFT) {
                moveMouse(Board.LEFT, stage);
            }
        });
    }

    @Override
    protected void moveBot(List<Integer> directionsList, Stage stage) {
        botMoveIndex = 0;
        translateTransition.setOnFinished(event -> {

            if (botMoveIndex == directionsList.size()) {
                return;
            }

            moveMouse(directionsList.get(botMoveIndex), stage);
            ++botMoveIndex;
        });
        translateTransition.setToX(0);
        translateTransition.setToY(0);
        translateTransition.play(); // play empty move to invoke OnFinished Method

        if (botMoveIndex == directionsList.size())
            createAlertBox(stage);
    }
}
