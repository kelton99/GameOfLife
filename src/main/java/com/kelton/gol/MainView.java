package com.kelton.gol;

import com.kelton.gol.model.Board;
import com.kelton.gol.model.CellState;
import com.kelton.gol.model.StandardRule;
import com.kelton.gol.viewmodel.ApplicationState;
import com.kelton.gol.viewmodel.ApplicationViewModel;
import com.kelton.gol.viewmodel.BoardViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    //canvas and the table are squared for simplicity
    public final int canvasSize = 600;
    public final int boardSize = 50;
    private final BoardViewModel boardViewModel;

    private InfoBar infoBar;

    private Canvas canvas;

    private Affine affine;

    private Board initialBoard;

    private CellState drawMode = CellState.ALIVE;

    private ApplicationViewModel appViewModel;

    private boolean isDrawingEnabled = true;

    public MainView(ApplicationViewModel appViewModel, BoardViewModel boardViewModel, Board initialBoard) {
        this.appViewModel = appViewModel;
        this.boardViewModel = boardViewModel;
        this.initialBoard = initialBoard;

        this.appViewModel.listenToAppState(this::onApplicationStateChanged);
        this.boardViewModel.listenToBoard(this::onBoardChanged);

        canvas = new Canvas(canvasSize, canvasSize);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this, appViewModel, boardViewModel);

        this.infoBar = new InfoBar();
        this.infoBar.setDrawMode(this.drawMode);
        this.infoBar.setCursorPosition(0, 0);
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, this.canvas, spacer, infoBar);

        this.affine = new Affine();
        this.affine.appendScale(canvasSize / boardSize, canvasSize / boardSize);

    }

    private void onBoardChanged(Board board) {
        draw(board);
    }

    private void onApplicationStateChanged(ApplicationState state){
        if(state == ApplicationState.EDITING) {
            this.isDrawingEnabled = true;
            this.boardViewModel.setBoard(this.initialBoard);
        }else if(state == ApplicationState.SIMULATING){
            this.isDrawingEnabled = false;
        }else{
            throw new IllegalArgumentException("Unsupported ApplicationState");
        }
    }

    private void handleMoved(MouseEvent mouseEvent) {
        Point2D simCoord = this.getSimulationCoordinates(mouseEvent);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();
        this.infoBar.setCursorPosition(simX, simY);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D)
            this.drawMode = CellState.ALIVE;
        else if (keyEvent.getCode() == KeyCode.E)
            this.drawMode = CellState.DEAD;
    }

    private void handleDraw(MouseEvent event) {
        if(!isDrawingEnabled) return;

        Point2D simCoord = this.getSimulationCoordinates(event);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();
        this.initialBoard.setState(simX, simY, drawMode);
        this.boardViewModel.setBoard(this.initialBoard);
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
            g.strokeLine(x, 0, x, boardSize);
        }
        for (int y = 0; y <= board.getHeight(); y++) {
            g.strokeLine(0, y, boardSize, y);
        }
    }

    private void drawSimulation(Board simulationToDraw){
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulationToDraw.getWidth(); x++) {
            for (int y = 0; y < simulationToDraw.getHeight(); y++) {
                if (simulationToDraw.getState(x, y) == CellState.ALIVE)
                    g.fillRect(x, y, 1, 1);
            }
        }
    }

    public void setDrawMode(CellState mode) {
        this.drawMode = mode;
        this.infoBar.setDrawMode(mode);
    }

}

