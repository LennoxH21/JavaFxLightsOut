package edu.bloomu.lightsout;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The game of Lights Out is played on a two-dimensional grid.
 *
 * Each cell of the grid contains a light. Two cells are said to be neighbors if they
 * share a common side. Initially some of the lights are on.
 *
 * To make a move, the player selects a cell: the light in the selected cell is toggled
 * (its ON(#)/OFF(.) switch is flipped) and so are the lights in the neighboring cells.
 *
 * The goal of the game is to turn all of the lights off.
 *
 * @author Lennox Haynes
 */

 class LightsOut {

    // grid[i][j] = true means the light in cell (i, j) is ON and OFF otherwise.
    private static boolean[][] grid;
    final int ROWS = 5;
    final int COLS = 5;

    private int amountOfMoves = -(ROWS * COLS);

    LightsOut() {
        grid = new boolean[ROWS][COLS];
        makeGrid(grid.length);
    }

    /**
     * Puts the grid into a starting state for the game.
     */
    private void makeGrid(int gridSize) {

        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int randMoves = gridSize * gridSize;

        for (int i = 0; i < randMoves; i++) {
            int row = rand.nextInt(gridSize);
            int col = rand.nextInt(gridSize);
            move(row, col);
        }
    }

    /**
     * Makes a move at cell (row, col)
     */
    void move(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            return;
        }
        toggle(row, col);
        toggle(row + 1, col);
        toggle(row -  1, col);
        toggle(row, col + 1);
        toggle(row, col - 1);


        amountOfMoves++;
    }

    /**
     * Returns true if the game is over (all lights are out)
     */
    boolean lightsOut() {

        for (boolean[] row: grid) {
            for (boolean lightOn: row) {
                if (lightOn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the boolean at the specified location
     */
    boolean lightAt(int row, int col) {
        return grid[row][col];
    }

    /**
     * Returns the amount of moves the user has made so far.
     */
    int getMoves() {
        return amountOfMoves;
    }

    /**
     * Flips the switch at
     */
    private void toggle(int row, int col) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid.length) {
            grid[row][col] = !grid[row][col];
        }
    }
}
