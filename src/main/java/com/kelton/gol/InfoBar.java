package com.kelton.gol;

import com.kelton.gol.model.CellState;
import com.kelton.gol.viewmodel.EditorViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {

    private final Label cursor;
    private final Label editingTool;

    public InfoBar(EditorViewModel editorViewModel) {
        editorViewModel.getDrawMode().listen(this::setDrawMode);
        this.cursor = new Label();
        this.editingTool = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setCursorPosition(0, 0);

        this.getChildren().addAll(this.editingTool, spacer, this.cursor);
    }

    private void setDrawMode(CellState drawMode){
        String drawModeString;
        if(drawMode == CellState.ALIVE)
            drawModeString = "Drawing";
        else
            drawModeString = "Erasing";

        String drawModeFormat = "Draw Mode: %s";
        this.editingTool.setText(String.format(drawModeFormat, drawModeString));
    }
    public void setCursorPosition(int x, int y){
        String cursorPositionFormat = "Cursor: (%d, %d)";
        this.cursor.setText(String.format(cursorPositionFormat, x, y));
    }

}
