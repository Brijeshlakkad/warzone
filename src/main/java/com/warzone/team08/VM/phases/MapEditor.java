package com.warzone.team08.VM.phases;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.map_editor.services.LoadMapService;
import com.warzone.team08.VM.map_editor.services.ShowMapService;

import java.util.List;

/**
 * ConcreteState of the State pattern. In this example, defines behavior for commands that are valid in this state, and
 * for the others signifies that the command is invalid.
 * <p>
 * This state represents a group of states, and defines the behavior that is common to all the states in its group. All
 * the states in its group need to extend this class.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public abstract class MapEditor extends Phase {
    /**
     * Parameterised constructor to create an instance of <code>Edit</code>.
     *
     * @param p_gameEngine Instance of the game engine.
     */
    MapEditor(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String loadMap(List<String> p_arguments) throws VMException {
        LoadMapService l_loadMapService = new LoadMapService();
        String l_returnValue = l_loadMapService.execute(p_arguments);
        d_gameEngine.setGamePhase(new PlaySetup(d_gameEngine));
        return l_returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String setPlayers(String p_serviceType, List<String> p_arguments) throws VMException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String assignCountries(List<String> p_arguments) throws VMException {
        return invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reinforce() throws VMException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void issueOrder() throws VMException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fortify() throws VMException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame(List<String> p_arguments) throws VMException {
        invalidCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String showMap(List<String> p_arguments) throws VMException {
        ShowMapService l_showMapService = new ShowMapService();
        return l_showMapService.execute(p_arguments);
    }
}
