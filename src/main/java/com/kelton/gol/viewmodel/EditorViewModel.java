package com.kelton.gol.viewmodel;

import com.kelton.gol.model.Board;
import com.kelton.gol.model.CellState;
import com.kelton.gol.util.Property;


public class EditorViewModel {

    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);

    private final BoardViewModel boardViewModel;
    private final Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard) {
        this.boardViewModel = boardViewModel;
        this.editorBoard = initialBoard;
    }

    public void onAppStateChange(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            this.boardViewModel.getBoard().set(editorBoard);
        } else {
            drawingEnabled = false;
        }
    }

    public void boardPressed(int simX, int simY) {
        if (drawingEnabled) {
            this.editorBoard.setState(simX, simY, drawMode.get());
            this.boardViewModel.getBoard().set(this.editorBoard);
        }
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }
}
