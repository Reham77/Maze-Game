package edu.maze.game.entity;

public class Board {
    public static final int DOWN = 0, UP = 1, RIGHT = 2, LEFT = 3;
    int rows, cols;
    private boolean[][] isRemoved;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        isRemoved = new boolean[(rows * cols) + 30][4];
    }

    public void removeWall(Cell cell, int direction) {

        assert (isRemoved[cell.getIdx(cols)][DOWN] == isRemoved[cell.getIdx(cols) + cols][UP]); //
        assert (isRemoved[cell.getIdx(cols)][UP] == isRemoved[cell.getIdx(cols) - cols][DOWN]); //
        assert (isRemoved[cell.getIdx(cols)][LEFT] == isRemoved[cell.getIdx(cols) - 1][RIGHT]); //
        assert (isRemoved[cell.getIdx(cols)][RIGHT] == isRemoved[cell.getIdx(cols) + 1][LEFT]); //
        switch (direction) {
            case DOWN:

                isRemoved[cell.getIdx(cols)][DOWN] = true;
                isRemoved[cell.getIdx(cols) + cols][UP] = true;
                break;
            case UP:

                isRemoved[cell.getIdx(cols)][UP] = true;
                isRemoved[cell.getIdx(cols) - cols][DOWN] = true;
                break;
            case RIGHT:

                isRemoved[cell.getIdx(cols)][RIGHT] = true;
                isRemoved[cell.getIdx(cols) + 1][LEFT] = true;
                break;
            case LEFT:

                isRemoved[cell.getIdx(cols)][LEFT] = true;
                isRemoved[cell.getIdx(cols) - 1][RIGHT] = true;
                break;
        }
    }

    public boolean isWallRemoved(Cell cell, int direction) {
        return isRemoved[cell.getIdx(cols)][direction];
    }

}
