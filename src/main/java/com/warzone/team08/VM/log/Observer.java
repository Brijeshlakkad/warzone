package com.warzone.team08.VM.log;

import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;

import java.io.IOException;

/**
 * This abstract class defines the methods to implement Observer.
 *
 * @author MILESH
 */
public abstract class Observer {
    Observable d_observable;

    /**
     * Constructor to initialize the observable object.
     * @param p_observable observable object
     */
    public Observer(Observable p_observable) {
        d_observable = p_observable;
        d_observable.attach(this);
    }

    /**
     * Abstract method of update method
     * @param p_observable_state Observable object
     * @throws IOException ioexception
     * @throws ResourceNotFoundException Throws if file not found.
     * @throws InvalidInputException Throws if file name is not valid.
     */
    public abstract void update(Observable p_observable_state) throws IOException, ResourceNotFoundException, InvalidInputException;
}