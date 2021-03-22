package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Player;


/**
 * This class implements the operations required to be perform when the Diplomacy(negotiate command) card is used.
 *
 * @author Deep Patel
 * @version 2.0
 */
public class DiplomacyService {

    private Player d_player1;
    private Player d_player2;

    /**
     * constructor initialize player with whom negotiation is happened.
     * @param p_player1 player1 object
     * @param p_player2 player2 object
     */
    public DiplomacyService(Player p_player1, Player p_player2)
    {
        d_player1 = p_player1;
        d_player2 = p_player2;
    }

    /**
     * execute method for adding player in each-others negotiation list.
     */
    public void execute() {
        d_player1.addNegotiatePlayer(d_player2);
        d_player2.addNegotiatePlayer(d_player1);
    }
}
