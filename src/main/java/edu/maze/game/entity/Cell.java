package edu.maze.game.entity;

public class Cell {
    public int i;
    public int j;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getIdx(int cols) {
        return ((i * cols) + j);
    }

    public Cell getNeighbourCell(int neighbourNum) {
        if (neighbourNum == Board.DOWN)
            return new Cell(i + 1, j);
        else if (neighbourNum == Board.UP)
            return new Cell(i - 1, j);
        else if (neighbourNum == Board.RIGHT)
            return new Cell(i, j + 1);
        else
            return new Cell(i, j - 1);
    }
}
