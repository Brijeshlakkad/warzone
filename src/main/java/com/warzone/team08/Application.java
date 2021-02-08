package com.warzone.team08;

import com.warzone.team08.CLI.CommandLineInterface;
import com.warzone.team08.CLI.constants.enums.states.GameState;

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

        // Creates interface for user interaction.
        CommandLineInterface l_userInteraction = new CommandLineInterface();

        l_userInteraction.d_thread.start();

        // Wait till the game is over.
        l_userInteraction.d_thread.join();
    }

    /**
     * To exist from the game
     */
    public static void exit() {
        Application.setIsRunning(false);
    }
}
