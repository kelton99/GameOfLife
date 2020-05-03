package com.kelton.gol;

import com.kelton.gol.model.CellState;
import com.kelton.gol.model.StandardRule;
import com.kelton.gol.viewmodel.ApplicationState;
import com.kelton.gol.viewmodel.ApplicationViewModel;
import com.kelton.gol.viewmodel.BoardViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private MainView view;
    private ApplicationViewModel viewModel;
    private BoardViewModel boardViewModel;
    private Simulator simulator;

    public Toolbar(MainView view, ApplicationViewModel viewModel, BoardViewModel boardViewModel) {

        this.view = view;
        this.viewModel = viewModel;
        this.boardViewModel = boardViewModel;

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
        this.viewModel.setCurrentState(ApplicationState.EDITING);
        this.simulator = null;
    }

    private void handleStep(ActionEvent actionEvent) {
        switchToSimulatingState();
        this.simulator.doStep();
    }

    private void switchToSimulatingState() {
        this.viewModel.setCurrentState(ApplicationState.SIMULATING);
        Simulation simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        this.simulator = new Simulator(this.boardViewModel, simulation);
    }

    private void handleErase(ActionEvent actionEvent) {
        this.view.setDrawMode(CellState.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        this.view.setDrawMode(CellState.ALIVE);
    }

}
