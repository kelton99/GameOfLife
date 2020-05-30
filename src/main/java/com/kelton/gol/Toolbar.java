package com.kelton.gol;

import com.kelton.gol.model.CellState;
import com.kelton.gol.viewmodel.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private final ApplicationViewModel viewModel;
    private final SimulationViewModel simulationViewModel;
    private final EditorViewModel editorViewModel;

    public Toolbar(EditorViewModel editorViewModel, ApplicationViewModel viewModel, SimulationViewModel simulationViewModel) {
        this.editorViewModel = editorViewModel;
        this.viewModel = viewModel;
        this.simulationViewModel = simulationViewModel;

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
        this.simulationViewModel.stop();
    }

    private void handleStart(ActionEvent actionEvent) {
        switchToSimulatingState();
        this.simulationViewModel.start();
    }

    private void handleReset(ActionEvent actionEvent) {
        this.viewModel.getApplicationState().set(ApplicationState.EDITING);
    }

    private void handleStep(ActionEvent actionEvent) {
        switchToSimulatingState();
        this.simulationViewModel.doStep();
    }

    private void switchToSimulatingState() {
        this.viewModel.getApplicationState().set(ApplicationState.SIMULATING);
    }

    private void handleErase(ActionEvent actionEvent) {
        this.editorViewModel.getDrawMode().set(CellState.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        this.editorViewModel.getDrawMode().set(CellState.ALIVE);
    }

}
