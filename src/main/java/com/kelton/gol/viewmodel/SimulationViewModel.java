package com.kelton.gol.viewmodel;

import com.kelton.gol.Simulation;
import com.kelton.gol.model.StandardRule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimulationViewModel {

    private final Timeline timeline;
    private final BoardViewModel boardViewModel;
    private Simulation simulation;

    public SimulationViewModel(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(150), event -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void onAppStateChange(ApplicationState state){
        if(state == ApplicationState.SIMULATING){
            this.simulation = new Simulation(boardViewModel.getBoard().get(), new StandardRule());
        }
    }

    public void doStep() {
        this.simulation.step();
        this.boardViewModel.getBoard().set(this.simulation.getBoard());
    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {
        this.timeline.stop();
    }
}
