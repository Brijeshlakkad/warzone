package com.warzone.team08.VM.phases;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.map_editor.services.ContinentService;
import com.warzone.team08.VM.map_editor.services.SaveMapService;
import com.warzone.team08.VM.map_editor.services.ValidateMapService;

import java.util.List;

/**
 * Concrete state of the <code>Phase</code>. Extends the <code>Edit</code>.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class PostLoad extends Edit {
    /**
     * Parameterised constructor to create an instance of <code>PostLoad</code>.
     *
     * @param p_gameEngine Instance of the game engine.
     */
    public PostLoad(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editMap(List<String> p_arguments) throws VMException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editContinent(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invokeMethod(new ContinentService(), l_serviceType, p_arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editCountry(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invokeMethod(new ContinentService(), l_serviceType, p_arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editNeighbor(String l_serviceType, List<String> p_arguments) throws VMException {
        return this.invokeMethod(new ContinentService(), l_serviceType, p_arguments);
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
        SaveMapService l_saveMapService = new SaveMapService();
        return l_saveMapService.execute(p_arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState() throws VMException {
        throw new VMException("Map must be loaded!");
    }
}
