package com.kelton.gol;

import com.kelton.gol.model.Board;
import com.kelton.gol.model.CellState;
import com.kelton.gol.model.SimulationRule;

public class Simulation {

    private SimulationRule simulationRule;
    private Board simulationBoard;

    public Simulation(Board simulationBoard, SimulationRule simulationRule) {
        this.simulationBoard = simulationBoard;
        this.simulationRule = simulationRule;
    }

    public void step() {
        Board nextState = simulationBoard.copy();

        for (int y = 0; y < simulationBoard.getHeight(); y++)
            for (int x = 0; x < simulationBoard.getWidth(); x++) {
                CellState newState = simulationRule.getNextState(x, y, simulationBoard);
                nextState.setState(x, y, newState);
            }

        this.simulationBoard = nextState;
    }

    public Board getBoard(){
        return simulationBoard;
    }

}
