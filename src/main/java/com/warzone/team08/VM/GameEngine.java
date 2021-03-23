package com.warzone.team08.VM;

import com.warzone.team08.VM.exceptions.ResourceNotFoundException;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.log.LogEntryBuffer;
import com.warzone.team08.VM.log.LogWriter;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.phases.Phase;
import com.warzone.team08.VM.phases.Preload;

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
     * State object of the GameEngine
     */
    private Phase d_gameState;

    LogEntryBuffer d_logEntryBuffer;
    LogWriter d_logWriter;

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
        d_Instance.setGamePhase(new Preload(this));
        // MAP_EDITOR ENGINE
        GameEngine.MAP_EDITOR_ENGINE().initialise();
        // GAME_PLAY ENGINE
        GameEngine.GAME_PLAY_ENGINE().initialise();

        d_logEntryBuffer = LogEntryBuffer.getLogger();
        try {
            d_logWriter = new LogWriter(d_logEntryBuffer);
        } catch (ResourceNotFoundException p_e) {
            VirtualMachine.getInstance().stderr("LogEntryBuffer failed!");
        }
    }

    /**
     * Signals its engines to shutdown.
     */
    public void shutdown() {
        MAP_EDITOR_ENGINE().shutdown();
        GAME_PLAY_ENGINE().shutdown();
    }

    /**
     * Sets new phase for the game.
     *
     * @param p_gamePhase New value of the game phase.
     */
    public void setGamePhase(Phase p_gamePhase) {
        d_gameState = p_gamePhase;
    }

    /**
     * Gets the phase of game.
     *
     * @return Value of the game phase.
     */
    public Phase getGamePhase() {
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