package com.warzone.team08.VM.entities.orders;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.repositories.PlayerRepository;


/**
 * This class implements the operations required to be perform when the Diplomacy(negotiate command) card is used.
 *
 * @author Deep Patel
 * @version 2.0
 */
public class NegotiateOrder implements Order {
    private final Player d_player1;
    private final Player d_player2;

    /**
     * To find the player using its data members.
     */
    private final PlayerRepository d_playerRepository = new PlayerRepository();

    /**
     * Parameterised constructor initialize player with whom negotiation is happened.
     *
     * @param p_thisPlayer  First player object.
     * @param p_otherPlayer Second player object.
     * @throws EntityNotFoundException Throws if the country with the given name doesn't exist.
     */
    public NegotiateOrder(Player p_thisPlayer, String p_otherPlayer) throws EntityNotFoundException {
        d_player1 = p_thisPlayer;
        d_player2 = d_playerRepository.findByPlayerName(p_otherPlayer);
    }

    /**
     * Executes the method for adding player in each-others negotiation list.
     */
    @Override
    public void execute() {
        d_player1.addNegotiatePlayer(d_player2);
        d_player2.addNegotiatePlayer(d_player1);
    }

    /**
     * Gets the type of order.
     *
     * @return Value of the order type.
     */
    public OrderType getType() {
        return OrderType.negotiate;
    }
}
