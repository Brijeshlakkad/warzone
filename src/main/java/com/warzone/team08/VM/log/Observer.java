package com.warzone.team08.VM.log;

import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;

import java.io.IOException;

/**
 * This interface is defines the methods to implement Observer.
 *
 * @author MILESH
 */
public abstract class Observer {
    Observable d_observable;

    public Observer(Observable p_observable) {
        d_observable = p_observable;
        d_observable.attach(this);
    }

    public abstract void update(Observable p_observable_state) throws IOException, ResourceNotFoundException, InvalidInputException;
}