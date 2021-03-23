package com.warzone.team08.VM.phases;

import com.warzone.team08.VM.GameEngine;
import com.warzone.team08.VM.exceptions.InvalidCommandException;
import com.warzone.team08.VM.exceptions.VMException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Base of the state of State pattern.
 * <p>
 * In this example, the states represent states in the board game Risk. There are many states, and even a hierarchy of
 * states:
 * </p>
 * <ul>
 *     <li>Phase</li>
 *     <li>Edit (abstract)</li>
 *     <li>
 *         <ul>
 *          <li>Preload</li>
 *          <li>Postload</li>
 *         </ul>
 *     </li>
 *     <li>Play (abstract)</li>
 *     <li>
 *         <ul>
 *           <li>PlaySetup</li>
 *           <li>MainPlay (abstract)</li>
 *           <li>
 *              <ul>
 *                <li>Reinforcement</li>
 *                <li>Attack</li>
 *                <li>Fortify</li>
 *               </ul>
 *            </li>
 *          </ul>
 *     </li>
 * <li>End</li>
 * </ul>
 * <p>
 * In each state, to go down in the above list, nextState() can be used, except for <code>Fortify</code>, which goes back to <code>Reinforcement</code> state.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public abstract class Phase {
    /**
     * Contains a reference of the <code>GameEngine</code> to change the phase of the
     * <code>GameEngine</code> between phases.
     */
    GameEngine d_gameEngine;

    /**
     * Parameterised constructor to create an instance of <code>Attack</code>.
     *
     * @param p_gameEngine Instance of the game engine.
     */
    Phase(GameEngine p_gameEngine) {
        d_gameEngine = p_gameEngine;
    }

    /**
     * Loads the map using the filename provided in the arguments.
     * <p>Uses <code>LoadMapService</code>.</p>
     *
     * @param p_arguments Contains the filename.
     * @return Response value of the operation.
     * @throws VMException If any exception while loading the file.
     * @see com.warzone.team08.VM.map_editor.services.LoadMapService
     */
    abstract public String loadMap(List<String> p_arguments) throws VMException;

    /**
     * Shows the map.
     * <p>Uses <code>ShowMapService</code>.</p>
     *
     * @param p_arguments Empty list.
     * @return Response value of the operation.
     * @throws VMException If any exception.
     * @see com.warzone.team08.VM.map_editor.services.SaveMapService
     */
    abstract public String showMap(List<String> p_arguments) throws VMException;

    /**
     * Allows player to edit the map.
     * <p>Uses <code>EditMapService</code>.</p>
     *
     * @param p_arguments Contains the filename.
     * @return Response value of the operation.
     * @throws VMException If any exception during loading the map into engines.
     * @see com.warzone.team08.VM.map_editor.services.EditMapService
     */
    abstract public String editMap(List<String> p_arguments) throws VMException;

    /**
     * Allows player to edit the continents on the map.
     * <p>Uses <code>ContinentService</code>.</p>
     *
     * @param l_serviceType Value of the method to be called on the service.
     * @param p_arguments   Contains the values to be passed to the method.
     * @return Response value of the operation.
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.map_editor.services.ContinentService
     */
    abstract public String editContinent(String l_serviceType, List<String> p_arguments) throws VMException;

    /**
     * Allows player to edit the countries of the continents on the map.
     * <p>Uses <code>CountryService</code>.</p>
     *
     * @param l_serviceType Value of the method to be called on the service.
     * @param p_arguments   Contains the values to be passed to the method.
     * @return Response value of the operation.
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.map_editor.services.CountryService
     */
    abstract public String editCountry(String l_serviceType, List<String> p_arguments) throws VMException;

    /**
     * Allows player to edit the neighbor-countries of the continents on the map.
     * <p>Uses <code>CountryNeighborService</code>.</p>
     *
     * @param l_serviceType Value of the method to be called on the service.
     * @param p_arguments   Contains the values to be passed to the method.
     * @return Response value of the operation.
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.map_editor.services.CountryNeighborService
     */
    abstract public String editNeighbor(String l_serviceType, List<String> p_arguments) throws VMException;

    /**
     * Validates the map.
     * <p>Uses <code>ValidateMapService</code>.</p>
     *
     * @param p_arguments Empty list.
     * @return Response value of the operation.
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.map_editor.services.ValidateMapService
     */
    abstract public String validateMap(List<String> p_arguments) throws VMException;

    /**
     * Saves the map to the specified file.
     * <p>Uses <code>SaveMapService</code>.</p>
     *
     * @param p_arguments Contains the filename.
     * @return Response value of the operation.
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.map_editor.services.SaveMapService
     */
    abstract public String saveMap(List<String> p_arguments) throws VMException;

    /**
     * Adds the player for this game.
     * <p>Uses <code>PlayerService</code>.</p>
     *
     * @param l_serviceType Value of the method to be called on the service.
     * @param p_arguments   Contains the values to be passed to the method.
     * @return Response value of the operation.
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.game_play.services.PlayerService
     */
    abstract public String setPlayers(String l_serviceType, List<String> p_arguments) throws VMException;

    /**
     * Assigns the countries to the players.
     * <p>Uses <code>DistributeCountriesService</code>.</p>
     *
     * @param p_arguments Empty list.
     * @return Response value of the operation.
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.game_play.services.DistributeCountriesService
     */
    abstract public String assignCountries(List<String> p_arguments) throws VMException;

    /**
     * Being called when the game phase enters the loop of <code>MainPlay</code>, to reinforce armies to each player.
     * <p>Uses <code>AssignReinforcementService</code>.</p>
     *
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.game_play.services.AssignReinforcementService
     */
    abstract public void reinforce() throws VMException;

    /**
     * To let players issue orders; Being called when the game phase enters the loop of <code>MainPlay</code>.
     * <p>Uses <code>IssueOrderService</code>.</p>
     *
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.game_play.services.IssueOrderService
     */
    abstract public void issueOrder() throws VMException;

    /**
     * <code>GameEngine</code> executes each player's orders; Being called when the game phase enters the loop of
     * <code>MainPlay</code>.
     * <p>Uses <code>ExecuteOrderService</code>.</p>
     *
     * @throws VMException Base class of any exception during the operation.
     * @see com.warzone.team08.VM.game_play.services.ExecuteOrderService
     */
    abstract public void fortify() throws VMException;

    /**
     * Ends the <code>MainPlay</code> game loop.
     *
     * @param p_arguments Empty list.
     * @throws VMException Base class of any exception during the operation.
     */
    abstract public void endGame(List<String> p_arguments) throws VMException;

    /**
     * To go to the next step.
     *
     * @throws VMException Base class of any exception during the operation.
     */
    abstract public void nextState() throws VMException;

    /**
     * A common method to all phases. Used when the method of the <code>Phase</code> is not available for specific
     * concrete state.
     *
     * @return Null as this method throws an exception.
     * @throws VMException This method always throws the <code>InvalidCommandException</code>.
     */
    public String invalidCommand() throws VMException {
        throw new InvalidCommandException("Invalid command!");
    }

    /**
     * Invokes the method of the target object using Java Reflections.
     * <p>
     * https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/index.html
     *
     * @param p_target     Object on which the method is to be called.
     * @param p_methodName Name of the method.
     * @param p_argValues  Arguments to be used at the time of method invocation.
     * @return Return value of the method invocation.
     * @throws VMException If the method throws any exception or method doesn't exist or the method is not accessible.
     */
    public String invokeMethod(Object p_target, String p_methodName, List<String> p_argValues) throws VMException {
        // Create two arrays:
        // 1. For type of the value
        // 2. For the values
        Class<?>[] l_valueTypes = new Class[p_argValues.size()];
        Object[] l_values = p_argValues.toArray();
        for (int l_argIndex = 0; l_argIndex < p_argValues.size(); l_argIndex++) {
            l_valueTypes[l_argIndex] = String.class;
        }

        // Get the reference and call the method with arguments
        try {
            Method l_methodReference = p_target.getClass().getMethod(p_methodName, l_valueTypes);
            return (String) l_methodReference.invoke(p_target, l_values);
        } catch (InvocationTargetException p_invocationTargetException) {
            throw new VMException(p_invocationTargetException.getMessage());
        } catch (NoSuchMethodException | IllegalAccessException p_e) {
            this.invalidCommand();
        }
        return null;
    }
}
