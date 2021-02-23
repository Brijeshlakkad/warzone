package com.warzone.team08.VM.game_play;

import com.warzone.team08.VM.constants.enums.GameLoopState;
import com.warzone.team08.VM.constants.interfaces.Engine;
import com.warzone.team08.VM.entities.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages players and their orders runtime information; Responsible for executing orders in round-robin fashion.
 *
 * @author Brijesh Lakkad
 * @author MILESH
 * @version 1.0
 */
public class GamePlayEngine implements Engine {
    /**
     * Singleton instance of the class.
     */
    private static GamePlayEngine d_instance;

    /**
     * Players of the game.
     */
    private List<Player> d_playerList;

    /**
     * Represents the current state of the game loop.
     * <ul>
     *     <li>not_available</li>
     *     <li>assign_reinforcements</li>
     *     <li>issue_order</li>
     *     <li>execute_order</li>
     * </ul>
     *
     * @see GameLoopState for more information.
     */
    private static GameLoopState gameLoopState;

    /**
     * Gets the single instance of the <code>GamePlayEngine</code> class.
     * <p>If not created before, it creates the one.
     *
     * @return Value of the instance.
     */
    public static GamePlayEngine getInstance() {
        if (d_instance == null) {
            d_instance = new GamePlayEngine();
        }
        return d_instance;
    }

    /**
     * Instance can not be created outside the class. (private)
     */
    private GamePlayEngine() {
        this.initialise();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialise() {
        d_playerList = new ArrayList<>();
        setGameLoopState(GameLoopState.NOT_AVAILABLE);
    }

    /**
     * Gets the state of <code>GameLoop</code>.
     *
     * @return Value of the state.
     */
    public static GameLoopState getGameLoopState() {
        return gameLoopState;
    }

    /**
     * Sets the state of <code>GameLoop</code>.
     *
     * @param p_gameLoopState Value of the state.
     */
    public static void setGameLoopState(GameLoopState p_gameLoopState) {
        gameLoopState = p_gameLoopState;
    }

    /**
     * Adds the player to the list.
     *
     * @param p_player Player to be added.
     */
    public void addPlayer(Player p_player) {
        d_playerList.add(p_player);
    }

    /**
     * Removes the player from the list.
     *
     * @param p_player Player to be removed.
     */
    public void removePlayer(Player p_player) {
        d_playerList.remove(p_player);
    }

    /**
     * Gets the players of the game.
     *
     * @return Value of the player list.
     */
    public List<Player> getPlayerList() {
        return d_playerList;
    }
}
