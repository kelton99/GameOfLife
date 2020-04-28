package com.kelton;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private MainView view;

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
        this.view.getSimulator().stop();
    }

    private void handleStart(ActionEvent actionEvent) {
        this.view.setApplicationState(MainView.SIMULATING);
        this.view.getSimulator().start();
    }

    private void handleReset(ActionEvent actionEvent) {
        this.view.setApplicationState(MainView.EDITING);
        this.view.draw();
    }

    private void handleStep(ActionEvent actionEvent) {

        this.view.setApplicationState(MainView.SIMULATING);
        this.view.getSimulation().step();
        this.view.draw();
    }

    private void handleErase(ActionEvent actionEvent) {
        this.view.setDrawMode(Simulation.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        this.view.setDrawMode(Simulation.ALIVE);
    }

}
