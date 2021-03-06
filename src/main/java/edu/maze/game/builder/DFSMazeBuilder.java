package edu.maze.game.builder;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import edu.maze.game.entity.Wall;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class DFSMazeBuilder extends MazeBuilder {
    private final boolean[][] visited;

    public DFSMazeBuilder(int rows, int cols) {
        super(rows, cols);
        visited = new boolean[rows + 2][cols + 2];
    }

    @Override
    protected boolean validCell(Cell cell) {
        return (board.inBounds(cell) && !visited[cell.i][cell.j]);
    }

    @Override
    public Board build() {
        Random random = new Random();
        Stack<Cell> Nodes = new Stack<>();
        Nodes.push(new Cell(0, 0));
        Cell currCell = new Cell(0, 0);
        visited[0][0] = true;

        while (!Nodes.empty()) {
            List<Wall> validOptions = getValidNeighbours(currCell);

            if (validOptions.isEmpty()) {
                currCell = Nodes.pop();
            } else {
                int rand = random.nextInt(validOptions.size());
                board.removeWall(currCell, validOptions.get(rand).direction);

                currCell = validOptions.get(rand).to;
                Nodes.push(currCell);
                visited[currCell.i][currCell.j] = true;
            }
        }
        return board;
    }
}

