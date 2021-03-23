package com.warzone.team08.VM.log;

import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;

import java.io.IOException;

/**
 * This interface defines the methods to implement the Observable.
 * @author MILESH
 * @throws ResourceNotFoundException Throws if file not found.
 * @throws InvalidInputException Throws if file name is not valid.
 * @throws IOException Throws if ioexception occurs.
 */
public interface Observable {
    public void attach(Observer o);
    public void detach(Observer o);
    public void notifyObservers(Observable p_o) throws ResourceNotFoundException, IOException, InvalidInputException;
}
