package com.kelton.gol;

import com.kelton.gol.model.Board;
import com.kelton.gol.model.BoundedBoard;
import com.kelton.gol.viewmodel.ApplicationState;
import com.kelton.gol.viewmodel.ApplicationViewModel;
import com.kelton.gol.viewmodel.BoardViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationViewModel appViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel = new BoardViewModel();
        Board board = new BoundedBoard(50, 50);

        MainView view = new MainView(appViewModel, boardViewModel, board);
        stage.setScene(new Scene(view, 1000, 700));
        stage.show();

        boardViewModel.setBoard(board);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
