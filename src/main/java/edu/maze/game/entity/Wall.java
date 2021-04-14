package edu.maze.game.entity;

public class Wall {
    public Cell from;
    public Cell to;
    public int direction;

    public Wall(Cell from, Cell to, int direction) {
        this.from = from;
        this.to = to;
        this.direction = direction;
    }
}
