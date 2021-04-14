package edu.maze.game.builder;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import edu.maze.game.entity.Wall;

import java.util.ArrayList;
import java.util.List;

public abstract class MazeBuilder {
    protected int rows, cols;
    protected Board board;

    public MazeBuilder(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new Board(rows, cols);
    }

    protected abstract boolean validCell(Cell cell);

    protected List<Wall> getValidNeighbours(Cell cell) {
        List<Wall> validCells = new ArrayList<>();
        for (int dir = 0; dir < 4; dir++) {
            Cell neighbourCell = cell.getNeighbourCell(dir);
            if (validCell(neighbourCell)) {
                validCells.add(new Wall(cell, neighbourCell, dir));
            }
        }
        return validCells;
    }

    public abstract Board build();
}
