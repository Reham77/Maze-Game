package edu.maze.game.builder;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import edu.maze.game.entity.Wall;

import java.util.List;
import java.util.Random;

public class PrimMazeBuilder extends MazeBuilder {

    boolean[][] visited;

    public PrimMazeBuilder(int rows, int cols) {
        super(rows, cols);
        visited = new boolean[rows + 2][cols + 2];
    }

    @Override
    protected boolean validCell(Cell cell) {
        return (inBounds(cell) && !visited[cell.i][cell.j]);
    }

    @Override
    public Board build() {
        Board board = new Board(rows, cols);
        Random random = new Random();

        Cell currCell = new Cell(0, 0);
        List<Wall> validWalls = getValidNeighbours(currCell);
        visited[0][0] = true;

        while (!validWalls.isEmpty()) {

            int randWall = random.nextInt(validWalls.size());
            currCell = validWalls.get(randWall).to;

            if (!visited[currCell.i][currCell.j]) {
                board.removeWall(validWalls.get(randWall).from, validWalls.get(randWall).direction);
                validWalls.addAll(getValidNeighbours(currCell));
            }
            visited[currCell.i][currCell.j] = true;
            validWalls.remove(randWall);

        }
        return board;
    }

}
