package com.warzone.team08.VM.common.services;

import com.warzone.team08.Application;
import com.warzone.team08.VM.constants.interfaces.SingleCommand;

import java.util.List;

/**
 * This class handles the request to handle <code>exit</code> request for this application.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class ApplicationExitService implements SingleCommand {
    /**
     * {@inheritDoc}
     *
     * @param p_commandValues Represents the values passed while running the command.
     * @return Value of execution response of the user command.
     */
    @Override
    public String execute(List<String> p_commandValues) {
        // Forces Application to exit.
        Application.exit();
        return null;
    }
}
