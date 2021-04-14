package edu.maze.game.builder;

import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import edu.maze.game.entity.Wall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMazeBuilder extends MazeBuilder {
    private final int[] parent = new int[rows * cols + 1];
    private final int[] sz = new int[rows * cols + 1];
    private final DSU dsu;
    private List<Wall> walls;

    public KruskalMazeBuilder(int rows, int cols) {
        super(rows, cols);
        dsu = new DSU();
    }

    @Override
    protected boolean validCell(Cell cell) {
        return board.inBounds(cell);
    }

    @Override
    public Board build() {
        dsu.init();
        for (Wall wall : walls) {
            Cell from = wall.from;
            Cell to = wall.to;

            if (!dsu.find(from.getIdx(rows), to.getIdx(rows))) {
                dsu.join(from.getIdx(rows), to.getIdx(rows));
                board.removeWall(from, wall.direction);
            }
        }
        return board;
    }

    private class DSU {
        private void generateWalls() {
            walls = new ArrayList<>();
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    walls.addAll(getValidNeighbours(new Cell(i, j)));
                }
            }
            Collections.shuffle(walls);
        }

        private void init() {
            for (int i = 1; i <= (rows * cols); ++i) {
                parent[i] = i;
                sz[i] = 1;
            }
            generateWalls();
        }

        private int root(int node) {
            while (node != parent[node]) {
                parent[node] = parent[parent[node]];
                node = parent[node];
            }
            return node;
        }

        private boolean join(int u, int v) {
            u = root(u);
            v = root(v);
            if (u == v)
                return false;

            if (sz[u] < sz[v]) {
                int temp = u;
                u = v;
                v = temp;
            }

            sz[u] += sz[v];
            parent[v] = u;
            return true;
        }

        boolean find(int u, int v) {
            return root(u) == root(v);
        }
    }
}