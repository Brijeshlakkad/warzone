package com.warzone.team08;

import com.warzone.team08.CLI.CommandLineInterface;
import com.warzone.team08.CLI.constants.enums.states.GameState;
import com.warzone.team08.VM.VirtualMachine;

/**
 * The main class of the War Zone Team08
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class Application {
    /**
     * False if user is interacting, meaning user is playing the game; true otherwise
     */
    private static volatile boolean d_isRunning = true;

    /**
     * Keeps track of the game state
     */
    private static GameState d_gameState = GameState.MAP_EDITOR;

    /**
     * Connects interface with method APIs; An environment for the player to store the information.
     */
    private static VirtualMachine d_virtualMachine;

    /**
     * Gets false if user is interacting, meaning user is playing the game; true otherwise.
     *
     * @return Value of false if user is interacting, meaning user is playing the game; true otherwise.
     */
    public static boolean isRunning() {
        return d_isRunning;
    }

    /**
     * Sets new false if user is interacting, meaning user is playing the game; true otherwise.
     *
     * @param d_isRunning New value of false if user is interacting, meaning user is playing the game; true otherwise.
     */
    public static void setIsRunning(boolean d_isRunning) {
        d_isRunning = d_isRunning;
    }

    /**
     * Sets new Keeps track of the game state.
     *
     * @param p_gameState New value of Keeps track of the game state.
     */
    public static void setGameState(GameState p_gameState) {
        d_gameState = p_gameState;
    }

    /**
     * Gets the state of the game
     *
     * @return Value of the game state
     */
    public static GameState getGameState() {
        return d_gameState;
    }

    public static void main(String[] args) throws InterruptedException {
        setIsRunning(true);

        // Starts the runtime engine (GameEngine) for the game.
        d_virtualMachine = new VirtualMachine();

        // Creates interface for user interaction.
        // Just a local variable as the instance is not being used/shared with any other class.
        // An instance of the virtual machine can be passed to the user interface?
        CommandLineInterface l_commandLineInterface = new CommandLineInterface();
        l_commandLineInterface.d_thread.start();

        // Wait till the game is over.
        l_commandLineInterface.d_thread.join();
    }

    /**
     * Gets the instance of virtual machine.
     *
     * @return Value of virtual machine.
     */
    public VirtualMachine VIRTUAL_MACHINE() {
        return d_virtualMachine;
    }

    /**
     * To exist from the game
     */
    public static void exit() {
        Application.setIsRunning(false);
    }
}
