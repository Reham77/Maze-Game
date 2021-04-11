package edu.maze.game.screen;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import javafx.animation.Animation;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.List;

public class MazeGameScene extends MazeGameDrawer {
    private int botMoveIndex;

    public MazeGameScene(int rows, int cols, int gameMode) {
        super(rows, cols, gameMode);
    }

    protected int getGridIdx(double num) {
        return ((int) num / (distance + 5));
    }

    private void moveMouse(int direction) {
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
    }

    @Override
    protected void sceneOnPressAction(Scene scene) {
        scene.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.UP) {
                moveMouse(Board.UP);
            } else if (key.getCode() == KeyCode.DOWN) {
                moveMouse(Board.DOWN);
            } else if (key.getCode() == KeyCode.RIGHT) {
                moveMouse(Board.RIGHT);
            } else if (key.getCode() == KeyCode.LEFT) {
                moveMouse(Board.LEFT);
            }
        });
    }

    @Override
    protected void moveBot(List<Integer> directionsList) {
        botMoveIndex = directionsList.size() - 1;
        translateTransition.setOnFinished(event -> {
            if (botMoveIndex == -1) {
                return;
            }
            moveMouse(directionsList.get(botMoveIndex));
            --botMoveIndex;
        });
        translateTransition.setToX(0);
        translateTransition.setToY(0);
        translateTransition.play(); // play empty move to invoke OnFinished Method
    }
}
