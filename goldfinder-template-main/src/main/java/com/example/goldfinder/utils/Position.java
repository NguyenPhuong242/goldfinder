package com.example.goldfinder.utils;

public class Position {
    public int column, row;

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Position(Position position) {
        this(position.column, position.row);
    }

    public boolean equals(Position position) {
        return column == position.column && row == position.row;
    }

    public String toString() {
        return "(" + column + ", " + row + ")";
    }

    public Position move(dir direction) {
        switch (direction) {
            case UP:
                return new Position(column, row - 1);
            case DOWN:
                return new Position(column, row + 1);
            case LEFT:
                return new Position(column - 1, row);
            case RIGHT:
                return new Position(column + 1, row);
            default:
                return new Position(column, row);
        }
    }
}
