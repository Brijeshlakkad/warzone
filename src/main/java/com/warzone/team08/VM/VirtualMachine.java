package com.warzone.team08.VM;

import com.warzone.team08.CLI.constants.states.GameState;
import com.warzone.team08.UserInterfaceMiddleware;
import com.warzone.team08.VM.exceptions.ExceptionHandler;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.MapEditorEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Handles the connection with different user interfaces. Creates an environment for the player to store the
 * information.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class VirtualMachine {
    /**
     * Singleton instance of the class.
     */
    private static VirtualMachine D_Instance;

    /**
     * Keeps track of the game state
     */
    private GameState d_gameState = GameState.NOT_STARTED;

    /**
     * List of User interface middleware. (Can be a stub/skeleton)
     */
    private UserInterfaceMiddleware d_userInterfaceMiddleware;

    private ExecutorService d_executor
            = Executors.newSingleThreadExecutor();

    /**
     * Creates the single instance of the <code>VirtualMachine</code> class.
     *
     * @return Value of the instance.
     */
    public static VirtualMachine newInstance() {
        D_Instance = new VirtualMachine();
        ExceptionHandler l_exceptionHandler = new ExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(l_exceptionHandler);
        return D_Instance;
    }

    /**
     * Terminates gracefully. Signals its engines to terminate.
     */
    public static void exit() {
        MAP_EDITOR_ENGINE().shutdown();
        GAME_PLAY_ENGINE().shutdown();
    }

    /**
     * Adds the user interface middleware instance to the list of <code>VM</code>.
     * <p>Stubs/Skeleton can be created if CLI and VM are on different machines.
     *
     * @param p_userInterfaceMiddleware Value of user interface middleware
     */
    public void attachUIMiddleware(UserInterfaceMiddleware p_userInterfaceMiddleware) {
        d_userInterfaceMiddleware = p_userInterfaceMiddleware;
    }

    /**
     * Gets the single instance of the <code>VirtualMachine</code> class which was created before.
     *
     * @return Value of the instance.
     * @throws NullPointerException Throws if the virtual machine instance was not created before.
     */
    public static VirtualMachine getInstance() throws NullPointerException {
        if (D_Instance == null) {
            throw new NullPointerException("Virtual Machine was not created. Something went wrong.");
        }
        return D_Instance;
    }

    /**
     * Sets new Keeps track of the game state.
     *
     * @param p_gameState New value of Keeps track of the game state.
     */
    public void setGameState(GameState p_gameState) {
        d_gameState = p_gameState;
    }

    /**
     * Gets the state of the game
     *
     * @return Value of the game state
     */
    public GameState getGameState() {
        return d_gameState;
    }

    /**
     * Sets the game state to <code>GAME_PLAY</code>
     */
    public void setGameStatePlaying() {
        this.setGameState(GameState.GAME_PLAY);
    }

    /**
     * Gets VM runtime map-editor engine to store map runtime information.
     *
     * @return Value of the map editor engine.
     */
    public static MapEditorEngine MAP_EDITOR_ENGINE() {
        return MapEditorEngine.getInstance();
    }

    /**
     * Gets VM runtime game-play engine to store runtime after game starts.
     *
     * @return Value of the map editor engine.
     */
    public static GamePlayEngine GAME_PLAY_ENGINE() {
        return GamePlayEngine.getInstance();
    }

    /**
     * Asks user interface for input/action.
     * <p>Used <code>asynchronous</code> operation as some requests may take longer than expected.</p>
     *
     * @param p_message Message to be shown before asking for input.
     * @return Value of the response to the request.
     */
    public Future<String> askForUserInput(String p_message) {
        return d_executor.submit(() ->
                d_userInterfaceMiddleware.askForUserInput(p_message)
        );
    }

    /**
     * Sends the message to output channel of the user interface.
     *
     * @param p_message Represents the message.
     */
    public void stdout(String p_message) {
        d_userInterfaceMiddleware.stdout(p_message);
    }

    /**
     * Sends the message to error channel of the user interface.
     *
     * @param p_message Represents the error message.
     */
    public void stderr(String p_message) {
        d_userInterfaceMiddleware.stderr(p_message);
    }
}
