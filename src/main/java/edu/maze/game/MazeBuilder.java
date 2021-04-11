package edu.maze.game;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import edu.maze.game.entity.Wall;

import java.util.ArrayList;
import java.util.List;

public abstract class MazeBuilder {

    protected final int DOWN = 0, UP = 1, RIGHT = 2, LEFT = 3;
    protected int rows, cols;

    public MazeBuilder(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    protected boolean inBounds(Cell cell) {
        return (cell.i >= 0 && cell.j >= 0 && cell.i < rows && cell.j < cols);
    }

    protected abstract boolean validCell(Cell cell);

    protected Cell getNeighbour(int neighbourNum, Cell cell) {
        if (neighbourNum == DOWN)
            return new Cell(cell.i + 1, cell.j);
        else if (neighbourNum == UP)
            return new Cell(cell.i - 1, cell.j);
        else if (neighbourNum == RIGHT)
            return new Cell(cell.i, cell.j + 1);
        else
            return new Cell(cell.i, cell.j - 1);
    }

    protected List<Wall> getValidNeighbours(Cell cell) {
        List<Wall> validCells = new ArrayList<>();
        for (int dir = 0; dir < 4; dir++) {
            Cell neighbourCell = getNeighbour(dir, cell);
            if (validCell(neighbourCell)) {
                validCells.add(new Wall(cell, neighbourCell, dir));
            }
        }
        return validCells;
    }

    public abstract Board build();
}
