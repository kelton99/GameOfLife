package com.kelton.gol.model;

import com.kelton.gol.Simulation;
import javafx.scene.control.Cell;

import static com.kelton.gol.model.CellState.DEAD;

public class BoundedBoard implements Board {

    private int width;
    private int height;
    private CellState[][] board;

    public BoundedBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new CellState[width][height];

        for (int y = 0; y < this.height; y++)
            for (int x = 0; x < this.width; x++)
                this.setState(x,y, DEAD);
    }


    @Override
    public BoundedBoard copy() {
        BoundedBoard newBoard = new BoundedBoard(this.width, this.height);

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                newBoard.setState(x,y, this.getState(x,y));
            }
        }
        return newBoard;
    }

    @Override
    public CellState getState(int x, int y) {
        if (x < 0 || x >= width) {
            return CellState.DEAD;
        }

        if (y < 0 || y >= height) {
            return CellState.DEAD;
        }

        return this.board[x][y];
    }

    @Override
    public void setState(int x, int y, CellState cellState) {
        if (x < 0 || x >= width) {
            return;
        }

        if (y < 0 || y >= height) {
            return;
        }

        this.board[x][y] = cellState;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() { return this.height; }
}
