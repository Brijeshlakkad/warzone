package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.Application;
import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.utils.PathResolverUtil;

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
            EntityNotFoundException {
        try {
            EditMapService l_editMapService = new EditMapService();
            // Resolve file path using absolute path of user data directory.
            String resolvedPathToFile = PathResolverUtil.resolveFilePath(p_commandValues.get(0));
            String response = l_editMapService.handleLoadMap(resolvedPathToFile, false);

            try {
                // Validates the map before saving the file.
                ValidateMapService l_validateObj = new ValidateMapService();
                l_validateObj.execute(null);
            } catch (InvalidMapException | EntityNotFoundException l_e) {
                MapEditorEngine.getInstance().initialise();
                throw l_e;
            }

            /*
             * Sets the game state to <code>Game Play</code>
             */
            Application.VIRTUAL_MACHINE().setGameStatePlaying();
            return response;
        } catch (ArrayIndexOutOfBoundsException p_e) {
            throw new InvalidInputException("File name is empty!");
        }
    }
}
