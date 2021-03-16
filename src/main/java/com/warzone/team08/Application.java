package com.warzone.team08;

import com.warzone.team08.CLI.CommandLineInterface;
import com.warzone.team08.CLI.constants.states.GameState;
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
    private static volatile boolean d_IsRunning = true;

    /**
     * Command-line user interface; Responsible for taking input from a user.
     */
    private static CommandLineInterface d_CommandLineInterface;

    /**
     * Connects interface with method APIs; An environment for the player to store the information.
     */
    private static VirtualMachine d_VirtualMachine;

    public Application() {
        // Creates interface for user interaction.
        // Just a local variable as the instance is not being used/shared with any other class.
        d_CommandLineInterface = new CommandLineInterface();

        // Starts the runtime engine for the game.
        // Virtual Machine will have the UI middleware.
        d_VirtualMachine = VirtualMachine.newInstance();

        // Attaches the CLI (stub) to VM.
        d_VirtualMachine.attachUIMiddleware(d_CommandLineInterface);
    }

    public static void main(String[] args) throws InterruptedException {
        Application l_application = new Application();

        // Sets the environment for game.
        l_application.handleApplicationStartup();

        // Starts the CLI
        l_application.handleCLIStartUp();
    }

    /**
     * Gets the instance of virtual machine.
     *
     * @return Value of virtual machine.
     */
    public static VirtualMachine VIRTUAL_MACHINE() {
        return d_VirtualMachine;
    }

    /**
     * Gets the state of the game
     *
     * @return Value of the game state
     */
    public static GameState getGameState() {
        return d_VirtualMachine.getGameState();
    }

    /**
     * Will be called when game starts.
     */
    public void handleApplicationStartup() {
        setIsRunning(true);
    }

    /**
     * Handles the startup of CLI.
     *
     * @throws InterruptedException If CLI interrupted by another thread.
     */
    public void handleCLIStartUp() throws InterruptedException {
        d_CommandLineInterface.d_thread.start();
        // Wait till the game is over.
        d_CommandLineInterface.d_thread.join();
    }

    /**
     * Gets false if user is interacting, meaning user is playing the game; true otherwise.
     *
     * @return Value of false if user is interacting, meaning user is playing the game; true otherwise.
     */
    public static boolean isRunning() {
        return d_IsRunning;
    }

    /**
     * Sets new false if user is interacting, meaning user is playing the game; true otherwise.
     *
     * @param p_isRunning New value of false if user is interacting, meaning user is playing the game; true otherwise.
     */
    public static void setIsRunning(boolean p_isRunning) {
        d_IsRunning = p_isRunning;
    }

    /**
     * To exist from the game
     */
    public static void exit() {
        Application.setIsRunning(false);
        VirtualMachine.exit();
        System.exit(0);
    }
}
