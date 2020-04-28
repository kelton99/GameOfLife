package com.kelton;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private MainView view;
    private Simulation simulation;

    public Simulator(MainView view, Simulation simulation) {
        this.view = view;
        this.simulation = simulation;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(100), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent) {
        this.simulation.step();
        this.view.draw();
    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {
        this.timeline.stop();
    }
}
