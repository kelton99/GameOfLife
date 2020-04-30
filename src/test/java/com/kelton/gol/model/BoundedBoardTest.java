package com.kelton.gol.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoundedBoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        this.board = new BoundedBoard(5, 3);
    }

    @Test
    void copy_sameSizeAsOriginal() {
        Board copy = board.copy();

        assertEquals(5, copy.getWidth());
        assertEquals(3, copy.getHeight());
    }

    @Test
    void copy_deepCopy() {
        Board copy = board.copy();

        copy.setState(3,2, CellState.ALIVE);

        assertEquals(CellState.ALIVE, copy.getState(3, 2));
        assertEquals(CellState.DEAD, board.getState(3,2));
    }

    @Test
    void copy_contentsAreTheSame() {
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(0, 2, CellState.ALIVE);
        
        Board copy = board.copy();

        for (int x = 0; x < copy.getWidth(); x++) {
            for (int y = 0; y < copy.getHeight(); y++) {
                assertEquals(board.getState(x, y), copy.getState(x,y));
            }
            
        }
    }

    @Test
    void setState_DoNotFailOutBounds() {
        board.setState(-1, 0, CellState.ALIVE);
        board.setState(5, 0, CellState.ALIVE);
        board.setState(0, -1, CellState.ALIVE);
        board.setState(0, 4, CellState.ALIVE);

    }

    @Test
    void getState_GetDoNotFailOutBounds() {
        board.getState(-1, 0);
        board.getState(5, 0);
        board.getState(0, -1);
        board.getState(0, 4);
    }

    @Test
    void setState_getState_returnsUpdatedResults() {
        board.setState(4, 1, CellState.ALIVE);
        assertEquals(CellState.ALIVE, board.getState(4, 1));
    }
}