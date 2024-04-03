package com.example.goldfinder.server;

import java.util.Random;

import com.example.goldfinder.utils.dir;
import com.example.goldfinder.utils.items;

public class Grid {
    boolean[][] hWall, vWall, gold;
    int columnCount, rowCount;

    private final Random random;

    public Grid(int columnCount, int rowCount, Random random) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.random = random;

        RandomMaze randomMaze = new RandomMaze(columnCount, rowCount, .1, random);
        randomMaze.generate();
        hWall = randomMaze.hWall;
        vWall = randomMaze.vWall;

        gold = new boolean[columnCount][rowCount];
        generateGold(3);
    }

    private void generateGold(double v) {
        for (int column = 0; column < columnCount; column++)
            for (int row = 0; row < rowCount; row++)
                gold[column][row] = (random.nextInt(10) < v);
    }

    boolean leftWall(int column, int row) {
        if (column == 0)
            return true;
        return vWall[column][row];
    }

    boolean rightWall(int column, int row) {
        if (column == columnCount - 1)
            return true;
        return vWall[column + 1][row];
    }

    boolean upWall(int column, int row) {
        if (row == 0)
            return true;
        return hWall[column][row];
    }

    boolean downWall(int column, int row) {
        if (row == rowCount - 1)
            return true;
        return hWall[column][row + 1];
    }

    boolean hasGold(int column, int row) {
        return gold[column][row];
    }

    boolean removeGold(int column, int row) {
        if (gold[column][row]) {
            gold[column][row] = false;
            return true;
        }
        return false;
    }

    boolean allCollectedGold() {
        for (int column = 0; column < columnCount; column++)
            for (int row = 0; row < rowCount; row++)
                if (gold[column][row])
                    return false;
        return true;
    }

    // is surrounding items: wall, gold, empty when the player is at position
    // (column, row), and facing direction dir
    // logic: if the player is facing a wall, then the player cannot move in that
    // direction
    // if the player is facing a gold, then the player can move in that direction
    // if the player is facing an empty space, then the player can move in that
    // direction
    public items surroundingItems(int column, int row, dir direction) {
        int neighborColumn = column;
        int neighborRow = row;
        switch (direction) {
            case UP:
                neighborRow--;
                break;
            case DOWN:
                neighborRow++;
                break;
            case LEFT:
                neighborColumn--;
                break;
            case RIGHT:
                neighborColumn++;
                break;
        }

        // Vérifiez les éléments environnants en fonction des coordonnées obtenues
        boolean hasLeftWall = leftWall(neighborColumn, neighborRow);
        boolean hasRightWall = rightWall(neighborColumn, neighborRow);
        boolean hasUpWall = upWall(neighborColumn, neighborRow);
        boolean hasDownWall = downWall(neighborColumn, neighborRow);
        boolean hasGold = hasGold(neighborColumn, neighborRow);

        // Retournez les éléments environnants sous forme d'enum items
        if (hasLeftWall && direction == dir.LEFT) {
            return items.WALL;
        } else if (hasRightWall && direction == dir.RIGHT) {
            return items.WALL;
        } else if (hasUpWall && direction == dir.UP) {
            return items.WALL;
        } else if (hasDownWall && direction == dir.DOWN) {
            return items.WALL;
        } else if (hasGold) {
            return items.GOLD;
        } else {
            return items.EMPTY;
        }
    }

    boolean isValidMove(int column, int row, dir direction) {
        return surroundingItems(column, row, direction) != items.WALL;
    }

}
