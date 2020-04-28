package com.kelton;

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

    public static final int EDITING = 0;
    public static final int SIMULATING = 1;

    //canvas and the table are squared for simplicity
    public final int canvasSize = 600;
    public final int tableSize = 50;

    private InfoBar infoBar;

    private Canvas canvas;

    private Affine affine;

    private Simulation simulation;
    private Simulation initialSimulation;

    private Simulator simulator;

    private int drawMode = Simulation.ALIVE;

    private int applicationState = EDITING;

    public MainView() {
        canvas = new Canvas(canvasSize, canvasSize);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this);

        this.infoBar = new InfoBar();
        this.infoBar.setDrawMode(this.drawMode);
        this.infoBar.setCursorPosition(0, 0);
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, this.canvas, spacer, infoBar);

        this.affine = new Affine();
        this.affine.appendScale(canvasSize / tableSize, canvasSize / tableSize);
        this.initialSimulation = new Simulation(tableSize, tableSize);
        this.simulation = Simulation.copy(this.initialSimulation);

    }

    private void handleMoved(MouseEvent mouseEvent) {
        Point2D simCoord = this.getSimulationCoordinates(mouseEvent);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();
        this.infoBar.setCursorPosition(simX, simY);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D)
            this.drawMode = Simulation.ALIVE;
        else if (keyEvent.getCode() == KeyCode.E)
            this.drawMode = Simulation.DEAD;
    }

    private void handleDraw(MouseEvent event) {
        if(applicationState == SIMULATING) return;

        Point2D simCoord = this.getSimulationCoordinates(event);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();
        this.initialSimulation.setState(simX, simY, drawMode);
        draw();
    }

    private Point2D getSimulationCoordinates(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);
            return simCoord;
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Non invertible transform");
        }
    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(affine);
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, canvasSize, canvasSize);

        if(this.applicationState == EDITING){
            drawSimulation(this.initialSimulation);
        }else{
            drawSimulation(this.simulation);
        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x < this.simulation.width + 1; x++) {
            g.strokeLine(x, 0, x, tableSize);
        }
        for (int y = 0; y < this.simulation.height + 1; y++) {
            g.strokeLine(0, y, tableSize, y);
        }
    }

    private void drawSimulation(Simulation simulationToDraw){
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulationToDraw.width; x++) {
            for (int y = 0; y < simulationToDraw.height; y++) {
                if (simulationToDraw.getState(x, y) == Simulation.ALIVE)
                    g.fillRect(x, y, 1, 1);
            }
        }
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void setDrawMode(int mode) {
        this.drawMode = mode;
        this.infoBar.setDrawMode(mode);
    }

    public void setApplicationState(int applicationState) {
        if(applicationState == this.applicationState)  return;

        if(applicationState == SIMULATING) {
            this.simulation = Simulation.copy(this.initialSimulation);
            this.simulator = new Simulator(this, this.simulation);
        }
        this.applicationState = applicationState;
    }

    public Simulator getSimulator() {
        return simulator;
    }
}
