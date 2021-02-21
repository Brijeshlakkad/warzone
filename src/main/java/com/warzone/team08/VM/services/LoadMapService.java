package com.warzone.team08.VM.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.exceptions.*;

import java.util.List;

/**
 * This file loads map file in the user console.
 * <p>
 * This service handles `loadmap` user command.
 *
 * @author Brijesh Lakkad
 * @author CHARIT
 */
public class LoadMapService implements SingleCommand {
    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(List<String> p_commandValues)
            throws InvalidMapException,
            ResourceNotFoundException,
            InvalidInputException,
            AbsentTagException,
            EntityNotFoundException {
        EditMapService l_editMapService = new EditMapService();
        String response = l_editMapService.execute(p_commandValues);
        // TODO Brijesh Lakkad: Change the game phase
        return response;
    }
}
