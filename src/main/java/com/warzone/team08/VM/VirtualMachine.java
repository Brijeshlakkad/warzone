package com.warzone.team08.VM;

import com.warzone.team08.CLI.constants.enums.states.GameState;
import com.warzone.team08.VM.engines.MapEditorEngine;

/**
 * Handles the connection with different user interfaces. Creates an environment for the player to store the
 * information.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class VirtualMachine {
    /**
     * Keeps track of the game state
     */
    private static GameState d_gameState = GameState.MAP_EDITOR;

    public VirtualMachine() {

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

    /**
     * Gets VM runtime map-editor engine to store map runtime information.
     *
     * @return Value of the map editor engine.
     */
    public MapEditorEngine MAP_EDITOR_ENGINE() {
        return MapEditorEngine.getInstance();
    }
}
