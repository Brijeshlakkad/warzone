package com.warzone.team08.VM;

import com.warzone.team08.CLI.constants.states.GameState;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.map_editor.MapEditorEngine;

/**
 * Handles the connection with different user interfaces. Creates an environment for the player to store the
 * information.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class GameEngine {
    /**
     * Singleton instance of the class.
     */
    private static GameEngine d_Instance;

    /**
     * Keeps track of the game state
     */
    private GameState d_gameState = GameState.NOT_STARTED;

    /**
     * Gets the single instance of the <code>GameEngine</code> class which was created before.
     *
     * @return Value of the instance.
     */
    public static GameEngine getInstance() throws NullPointerException {
        if (d_Instance == null) {
            d_Instance = new GameEngine();
            d_Instance.initialise();
        }
        return d_Instance;
    }

    /**
     * Initialise all the engines to reset the runtime information.
     */
    public void initialise() {
        d_Instance.setGameState(GameState.MAP_EDITOR);
        // MAP_EDITOR ENGINE
        GameEngine.MAP_EDITOR_ENGINE().initialise();
        // GAME_PLAY ENGINE
        GameEngine.GAME_PLAY_ENGINE().initialise();
    }

    /**
     * Signals its engines to shutdown.
     */
    public void shutdown() {
        MAP_EDITOR_ENGINE().shutdown();
        GAME_PLAY_ENGINE().shutdown();
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
     * @return Value of the game-play engine.
     */
    public static GamePlayEngine GAME_PLAY_ENGINE() {
        return GamePlayEngine.getInstance();
    }
}
