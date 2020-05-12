package com.kelton.gol.viewmodel;

import com.kelton.gol.Simulation;
import com.kelton.gol.model.Board;
import com.kelton.gol.model.CellState;

import java.util.ArrayList;
import java.util.List;

public class EditorViewModel {

    private CellState drawMode = CellState.ALIVE;
    private List<SimpleChangeListener<CellState>> drawModeListeners;
    private Board initialBoard;
    private boolean drawingEnabled;

    public EditorViewModel(Board initialBoard) {
        this.initialBoard = initialBoard;
        this.drawModeListeners = new ArrayList<>();
    }

    public void listenToDrawMode(SimpleChangeListener<CellState> listener){

    }

    public void setDrawMode(CellState drawMode){
        this.drawMode = drawMode;
        notifyDrawModeListeners();
    }

    private void notifyDrawModeListeners() {
        for (SimpleChangeListener<CellState> drawModeListener : drawModeListeners) {
            drawModeListener.valueChanged(drawMode);
        }
    }
}
