package com.warzone.team08.VM.log;

import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Observable to notify the observers about the changes happen during any actions.
 * @author MILESH
 */
public class LogEntryBuffer implements Observable {
    private List<Observer> d_observerList;
    private String d_message="";

    public LogEntryBuffer(){
        d_observerList=new ArrayList<>();
        d_observerList.add(new LogWriter());
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
    public void notifyObservers(Observable p_o) throws ResourceNotFoundException, IOException, InvalidInputException {
        for(Observer l_observer:d_observerList){
            l_observer.update(p_o);
        }
    }

    public String getMessage() {
        return d_message;
    }

    public void setMessage(String p_message) {
        d_message = p_message;
    }

    public void dataChanged(String p_headCommand,String p_message) throws IOException, ResourceNotFoundException, InvalidInputException {
        d_message=p_message;
        notifyObservers(this);
        d_message="";
    }
}
