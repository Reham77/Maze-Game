package edu.maze.game;

public class Wall {

    public Cell from;
    public Cell to;
    int direction;

    public Wall(Cell from, Cell to, int direction) {
        this.from = from;
        this.to = to;
        this.direction = direction;
    }
}
