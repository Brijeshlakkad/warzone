package com.warzone.team08.VM;

import com.warzone.team08.VM.exceptions.ResourceNotFoundException;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.logger.LogEntryBuffer;
import com.warzone.team08.VM.logger.LogWriter;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.phases.Phase;
import com.warzone.team08.VM.phases.Preload;

/**
 * Creates an environment for the player to store the information.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class GameEngine {
    /**
     * State object of the GameEngine
     */
    private Phase d_gameState;

    /**
     * <code>MapEditorEngine</code> for this game.
     * VM runtime map-editor engine to store map runtime information.
     */
    private MapEditorEngine d_mapEditorEngine;

    /**
     * <code>GamePlayEngine</code> for this game.
     * VM runtime game-play engine to store runtime after game starts.
     */
    private GamePlayEngine d_gamePlayEngine;

    private boolean d_isTournamentModeOn = false;

    LogEntryBuffer d_logEntryBuffer;
    LogWriter d_logWriter;

    /**
     * Default constructor.
     */
    public GameEngine() {
        this.initialise();
        // MAP_EDITOR ENGINE
        d_mapEditorEngine = new MapEditorEngine();
        d_mapEditorEngine.initialise();
        // GAME_PLAY ENGINE
        d_gamePlayEngine = new GamePlayEngine();
        d_gamePlayEngine.initialise();
    }

    /**
     * Sets the MapEditor and GamePlay engines for this tournament round. This method will be used at the time of
     * tournament. Calling of this method also sets the game mode to tournament.
     *
     * @param p_mapEditorEngine MapEditor engine.
     * @param p_gamePlayEngine  GamePlay engine.
     */
    public GameEngine(MapEditorEngine p_mapEditorEngine, GamePlayEngine p_gamePlayEngine) {
        this.initialise();
        d_mapEditorEngine = p_mapEditorEngine;
        d_gamePlayEngine = p_gamePlayEngine;
        d_isTournamentModeOn = true;
    }

    /**
     * Initialise all the engines to reset the runtime information.
     */
    public void initialise() {
        this.setGamePhase(new Preload(this));
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
        d_mapEditorEngine.shutdown();
        d_gamePlayEngine.shutdown();
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
    public MapEditorEngine getMapEditorEngine() {
        return d_mapEditorEngine;
    }

    /**
     * Gets VM runtime game-play engine to store runtime after game starts.
     *
     * @return Value of the game-play engine.
     */
    public GamePlayEngine getGamePlayEngine() {
        return d_gamePlayEngine;
    }

    /**
     * Check if the tournament mode is on.
     *
     * @return True if the game mode is tournament; false otherwise.
     */
    public boolean isTournamentModeOn() {
        return d_isTournamentModeOn;
    }
}