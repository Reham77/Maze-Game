package edu.maze.game.entity;

public class Cell {
    public int i;
    public int j;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
    }

    int getIdx(int cols) {
        return ((i * cols) + j);
    }

}
