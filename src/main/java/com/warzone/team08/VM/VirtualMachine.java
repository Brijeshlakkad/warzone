package com.warzone.team08.VM;

import com.warzone.team08.UserInterfaceMiddleware;
import com.warzone.team08.VM.exceptions.ExceptionHandler;
import com.warzone.team08.VM.phases.Phase;

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
    private static VirtualMachine d_Instance;

    /**
     * List of User interface middleware. (Can be a stub/skeleton)
     */
    private UserInterfaceMiddleware d_userInterfaceMiddleware;

    /**
     * To execute <code>Future</code> tasks.
     */
    private final ExecutorService d_executor = Executors.newFixedThreadPool(10);

    /**
     * Creates the single instance of the <code>VirtualMachine</code> class.
     *
     * @return Value of the instance.
     */
    public static VirtualMachine newInstance() {
        d_Instance = new VirtualMachine();
        d_Instance.initialise();
        // Default exception handler.
//        ExceptionHandler l_exceptionHandler = new ExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(l_exceptionHandler);
        return d_Instance;
    }

    /**
     * Initialise engine to reset the runtime information.
     */
    public void initialise() {
        // Prepare instances.
        VirtualMachine.GAME_ENGINE().initialise();
    }

    /**
     * Terminates gracefully. Signals its engines to terminate.
     */
    public static void exit() {
        GAME_ENGINE().shutdown();
        VirtualMachine.getInstance().stdout("Shutting down...");
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
        if (d_Instance == null) {
            throw new NullPointerException("Virtual Machine was not created. Something went wrong.");
        }
        return d_Instance;
    }

    /**
     * Gets game engine to store runtime information of the game.
     *
     * @return Value of the game engine.
     */
    public static GameEngine GAME_ENGINE() {
        return GameEngine.getInstance();
    }

    /**
     * Gets the state of the game
     *
     * @return Value of the game state
     */
    public static Phase getGamePhase() {
        return GAME_ENGINE().getGamePhase();
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
