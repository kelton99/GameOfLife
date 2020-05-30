package com.kelton.gol;

import com.kelton.gol.model.Board;
import com.kelton.gol.model.BoundedBoard;
import com.kelton.gol.view.SimulationCanvas;
import com.kelton.gol.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        ApplicationViewModel appViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board board = new BoundedBoard(20, 20);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, board);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel);

        appViewModel.getApplicationState().listen(editorViewModel::onAppStateChange);
        appViewModel.getApplicationState().listen(simulationViewModel::onAppStateChange);

        boardViewModel.getBoard().set(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(editorViewModel, boardViewModel);
        VBox.setVgrow(simulationCanvas, Priority.ALWAYS);
        Toolbar toolbar = new Toolbar(editorViewModel, appViewModel, simulationViewModel);
        InfoBar infoBar = new InfoBar(editorViewModel);

        MainView view = new MainView(editorViewModel);
        view.setTop(toolbar);
        view.setCenter(simulationCanvas);
        view.setBottom(infoBar);
        stage.setScene(new Scene(view, 1200, 800));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
