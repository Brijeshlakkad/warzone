package com.warzone.team08.VM.phases;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.map_editor.services.EditMapService;
import com.warzone.team08.VM.map_editor.services.ValidateMapService;

import java.io.IOException;
import java.util.List;

/**
 * <code>Preload</code> phase of the game phase. This is the initial phase of the game.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class Preload extends MapEditor {
    /**
     * Parameterised constructor to create an instance of <code>Preload</code>.
     *
     * @param p_gameEngine Instance of the game engine.
     */
    public Preload(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editMap(List<String> p_arguments) throws VMException, IOException {
        EditMapService l_editMapService = new EditMapService();
        String l_returnValue = l_editMapService.execute(p_arguments);
        d_gameEngine.setGamePhase(new PostLoad(d_gameEngine));
        return l_returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editContinent(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editCountry(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editNeighbor(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateMap(List<String> p_arguments) throws VMException {
        ValidateMapService l_validateMapService = new ValidateMapService();
        return l_validateMapService.execute(p_arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveMap(List<String> p_arguments) throws VMException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState() throws VMException {
        throw new VMException("Map hasn't been loaded.");
    }
}
