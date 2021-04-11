package edu.maze.game.bot;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;

import java.util.*;

public class BotPlay {
    private final boolean[][] visited;
    private final Cell[][] parentCell;
    private final int[][] parentDir;
    private final int rows;
    private final int cols;

    public BotPlay(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        visited = new boolean[rows + 2][cols + 2];
        parentCell = new Cell[rows + 2][cols + 2];
        parentDir = new int[rows + 2][cols + 2];
    }

    private boolean validPath(Cell from, Cell to, int direction, Board board) {
        return (board.inBounds(to) && board.isWallRemoved(from, direction) && !visited[to.i][to.j]);
    }

    public void bfs(Board board) {
        Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(0, 0));
        Cell target = new Cell(rows - 1, cols - 1);
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Cell currCell = queue.peek();
            queue.remove();
            if (currCell.equals(target)) {
                return;
            }

            for (int dir = 0; dir < 4; ++dir) {
                Cell newCell = currCell.getNeighbourCell(dir);
                if (validPath(currCell, newCell, dir, board)) {
                    queue.add(newCell);
                    visited[newCell.i][newCell.j] = true;
                    parentCell[newCell.i][newCell.j] = currCell;
                    parentDir[newCell.i][newCell.j] = dir;
                }
            }
        }
    }

    public List<Integer> getPath(Board board) {
        bfs(board);
        List<Integer> path = new ArrayList<>();
        path.add(parentDir[rows - 1][cols - 1]);

        Cell parent = parentCell[rows - 1][cols - 1];
        while (parentCell[parent.i][parent.j] != null) {
            path.add(parentDir[parent.i][parent.j]);
            parent = parentCell[parent.i][parent.j];
        }
        Collections.reverse(path);
        return path;
    }

}
