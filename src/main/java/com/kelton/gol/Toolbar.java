package com.kelton.gol;

import com.kelton.gol.model.CellState;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private MainView view;
    private Simulator simulator;

    public Toolbar(MainView view){

        this.view = view;

        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);

        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);

        Button step = new Button("Step");
        step.setOnAction(this::handleStep);

        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);

        Button start = new Button("Run");
        start.setOnAction(this::handleStart);

        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);


        this.getItems().addAll(draw, erase, reset, step, start, stop);
    }

    private void handleStop(ActionEvent actionEvent) {
        this.simulator.stop();
    }

    private void handleStart(ActionEvent actionEvent) {
        switchToSimulatingState();
        this.simulator.start();
    }

    private void handleReset(ActionEvent actionEvent) {
        this.view.setApplicationState(MainView.EDITING);
        this.simulator = null;
        this.view.draw();
    }

    private void handleStep(ActionEvent actionEvent) {
        switchToSimulatingState();
        this.view.getSimulation().step();
        this.view.draw();
    }

    private void switchToSimulatingState(){
        if(this.view.getApplicationState() == MainView.EDITING){
            this.view.setApplicationState(MainView.SIMULATING);
            this.simulator = new Simulator(this.view, this.view.getSimulation());
        }
    }

    private void handleErase(ActionEvent actionEvent) {
        this.view.setDrawMode(CellState.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        this.view.setDrawMode(CellState.ALIVE);
    }

}
