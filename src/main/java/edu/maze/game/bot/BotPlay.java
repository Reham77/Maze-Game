package edu.maze.game.bot;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BotPlay {
    private boolean[][] visited;
    private Cell[][] parent;
    private int[][] dir;
    private int rows, cols;

    public BotPlay(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        visited = new boolean[rows + 2][cols + 2];
        parent = new Cell[rows + 2][cols + 2];
        dir = new int[rows + 2][cols + 2];
    }

    protected boolean inBounds(Cell cell) {
        return (cell.i >= 0 && cell.j >= 0 && cell.i < rows && cell.j < cols);
    }

    private boolean validPath(Cell from, Cell to, int direction, Board board) {
        return (inBounds(to) && board.isWallRemoved(from, direction) && !visited[to.i][to.j]);
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
            if (validPath(currCell, new Cell(currCell.i + 1, currCell.j), Board.DOWN, board)) {
                queue.add(new Cell(currCell.i + 1, currCell.j));
                visited[currCell.i + 1][currCell.j] = true;
                parent[currCell.i + 1][currCell.j] = currCell;
                dir[currCell.i + 1][currCell.j] = Board.DOWN;
            }

            if (validPath(currCell, new Cell(currCell.i - 1, currCell.j), Board.UP, board)) {
                queue.add(new Cell(currCell.i - 1, currCell.j));
                visited[currCell.i - 1][currCell.j] = true;
                parent[currCell.i - 1][currCell.j] = currCell;
                dir[currCell.i - 1][currCell.j] = Board.UP;
            }
            if (validPath(currCell, new Cell(currCell.i, currCell.j + 1), Board.RIGHT, board)) {
                queue.add(new Cell(currCell.i, currCell.j + 1));
                visited[currCell.i][currCell.j + 1] = true;
                parent[currCell.i][currCell.j + 1] = currCell;
                dir[currCell.i][currCell.j + 1] = Board.RIGHT;

            }
            if (validPath(currCell, new Cell(currCell.i, currCell.j - 1), Board.LEFT, board)) {
                queue.add(new Cell(currCell.i, currCell.j - 1));
                visited[currCell.i][currCell.j - 1] = true;
                parent[currCell.i][currCell.j - 1] = currCell;
                dir[currCell.i][currCell.j - 1] = Board.LEFT;
            }
        }
    }

    public List<Integer> getPath(Board board) {
        bfs(board);
        List<Integer> path = new ArrayList<>();
        path.add(dir[rows - 1][cols - 1]);

        Cell p = parent[rows - 1][cols - 1];
        while (parent[p.i][p.j] != null) {
            path.add(dir[p.i][p.j]);
            System.out.println(p.i + " " + p.j);
            p = parent[p.i][p.j];
        }
        return path;
    }

}
