package com.kelton.gol.view;

import com.kelton.gol.model.Board;
import com.kelton.gol.model.CellState;
import com.kelton.gol.viewmodel.BoardViewModel;
import com.kelton.gol.viewmodel.EditorViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class SimulationCanvas extends Pane {

    public final int canvasSize = 600;

    private Canvas canvas;

    private final Affine affine;

    private EditorViewModel editorViewModel;
    private BoardViewModel boardViewModel;

    public SimulationCanvas(EditorViewModel editorViewModel, BoardViewModel boardViewModel) {
        this.editorViewModel = editorViewModel;
        this.boardViewModel = boardViewModel;
        boardViewModel.getBoard().listen(this::draw);

        canvas = new Canvas(canvasSize, canvasSize);

        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(canvas);
        //this.canvas.setOnMouseMoved(this::handleMoved);

        this.affine = new Affine();
        this.affine.appendScale(canvasSize / boardViewModel.getBoard().get().getWidth(), canvasSize / boardViewModel.getBoard().get().getHeight());

    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        draw(boardViewModel.getBoard().get());
    }

    private void handleDraw(MouseEvent event) {

        Point2D simCoord = this.getSimulationCoordinates(event);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        this.editorViewModel.boardPressed(simX, simY);
    }

    private Point2D getSimulationCoordinates(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        try {
            return this.affine.inverseTransform(mouseX, mouseY);
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Non invertible transform");
        }
    }

    public void draw(Board board) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(affine);
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, canvasSize, canvasSize);

        this.drawSimulation(board);

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= board.getWidth(); x++) {
            g.strokeLine(x, 0, x, board.getWidth());
        }
        for (int y = 0; y <= board.getHeight(); y++) {
            g.strokeLine(0, y, board.getHeight(), y);
        }
    }

    private void drawSimulation(Board simulationToDraw) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulationToDraw.getWidth(); x++) {
            for (int y = 0; y < simulationToDraw.getHeight(); y++) {
                if (simulationToDraw.getState(x, y) == CellState.ALIVE)
                    g.fillRect(x, y, 1, 1);
            }
        }
    }

//   private void handleMoved(MouseEvent mouseEvent) {
//        Point2D simCoord = this.getSimulationCoordinates(mouseEvent);
//        int simX = (int) simCoord.getX();
//        int simY = (int) simCoord.getY();
//        this.infoBar.setCursorPosition(simX, simY);
//    }

}
