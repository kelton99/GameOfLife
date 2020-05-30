package com.kelton.gol.viewmodel;

import com.kelton.gol.util.Property;

public class ApplicationViewModel {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

    public ApplicationViewModel() {

    }

    public Property<ApplicationState> getApplicationState() {
        return applicationState;
    }
}
