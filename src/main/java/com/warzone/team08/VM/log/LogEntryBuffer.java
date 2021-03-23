package com.warzone.team08.VM.log;

import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Observable to notify the observers about the changes happen during any actions.
 *
 * @author MILESH
 */
public class LogEntryBuffer implements Observable {
    private List<Observer> d_observerList;
    private String d_message;
    private String d_headCommand;
    private static LogEntryBuffer d_instance;

    private LogEntryBuffer() {
        d_observerList = new ArrayList<>();
    }

    public static LogEntryBuffer getLogger() {
        if (d_instance == null) {
            d_instance = new LogEntryBuffer();
        }
        return d_instance;
    }

    @Override
    public void attach(Observer p_observer) {
        d_observerList.add(p_observer);
    }

    @Override
    public void detach(Observer p_observer) {
        d_observerList.remove(p_observer);
    }

    @Override
    public void notifyObservers(Observable p_o) throws ResourceNotFoundException, InvalidInputException {
        for (Observer l_observer : d_observerList) {
            try {
                l_observer.update(p_o);
            } catch (IOException p_e) {
                p_e.printStackTrace();
            }
        }
    }

    public String getMessage() {
        return d_message;
    }

    public void setMessage(String p_message) {
        d_message = p_message;
    }

    public void dataChanged(String p_headCommand, String p_message) throws ResourceNotFoundException, InvalidInputException {
        d_headCommand = p_headCommand;
        d_message = p_message;
        notifyObservers(this);
        d_message = "";
    }

    public String getHeadCommand() {
        return d_headCommand;
    }

    public void setHeadCommand(String p_headCommand) {
        d_headCommand = p_headCommand;
    }
}