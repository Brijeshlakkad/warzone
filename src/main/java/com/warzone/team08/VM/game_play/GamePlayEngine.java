package com.warzone.team08.VM.game_play;

import com.warzone.team08.VM.constants.interfaces.Engine;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import java.util.ArrayList;

/**
 * Manages players and their orders runtime information; Responsible for executing orders in round-robin
 * fashion.
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
    private ArrayList<Player> d_playerList;

    /**
     * Gets the single instance of the class.
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
    public void initialise() {
        d_playerList = new ArrayList<>();
    }

    /**
     * Sets the list of player
     * @param p_playerList players list
     */
    public void setPlayerList(ArrayList<Player> p_playerList) {
        d_playerList = p_playerList;
    }

    /**
     * Gets the list of players
     * @return players list
     */
    public ArrayList<Player> getPlayerList() {
        return d_playerList;
    }

    /**
     * Adds the element to the list of players.
     *
     * @param p_player Value of the element.
     */
    public  void addPlayer(Player p_player){
        d_playerList.add(p_player);
    }

}
