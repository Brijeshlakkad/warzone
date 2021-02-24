package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.Application;
import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.exceptions.*;

import java.io.IOException;
import java.util.List;

/**
 * This file loads map file in the user console.
 * <p>
 * This service handles `loadmap` user command.
 *
 * @author Brijesh Lakkad
 */
public class LoadMapService implements SingleCommand {
    /**
     * {@inheritDoc}
     *
     * @see EditMapService#handleLoadMap
     */
    @Override
    public String execute(List<String> p_commandValues)
            throws InvalidMapException,
            ResourceNotFoundException,
            InvalidInputException,
            AbsentTagException,
            EntityNotFoundException, IOException {
        EditMapService l_editMapService = new EditMapService();
        String response = l_editMapService.execute(p_commandValues);
        /*
         * Sets the game state to <code>Game Play</code>
         */
        Application.VIRTUAL_MACHINE().setGameStatePlaying();
        return response;
    }
}
