package com.kelton.gol.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardRuleTest {

    private Board board;
    private SimulationRule simulationRule;

    @BeforeEach
    void setUp() {
        board = new BoundedBoard(3,3);
        simulationRule = new StandardRule();
    }

    @Test
    void getNextState_zeroNeighborAlive() {
        board.setState(1,1,CellState.ALIVE);
        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, board.getState(1,1));
    }

    @Test
    void getNextState_oneNeighborAlive() {
        board.setState(1,1,CellState.ALIVE);
        board.setState(1,2,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, board.getState(1,1));
    }
    @Test
    void getNextState_twoNeighborAlive() {
        board.setState(1,1,CellState.ALIVE);
        board.setState(1,2,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, board.getState(1,1));
    }
    @Test
    void getNextState_threeNeighborAlive() {
        board.setState(1,1,CellState.ALIVE);
        board.setState(1,2,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, board.getState(1,1));
    }
    @Test
    void getNextState_fourNeighborAlive() {
        board.setState(1,1,CellState.ALIVE);
        board.setState(1,2,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, board.getState(1,1));
    }

}