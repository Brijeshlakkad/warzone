package com.warzone.team08.VM.entities.orders;

import com.warzone.team08.VM.constants.enums.CardType;
import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Card;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.CardNotFoundException;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.logger.LogEntryBuffer;
import com.warzone.team08.VM.repositories.PlayerRepository;


/**
 * This class implements the operations required to be perform when the Diplomacy(negotiate command) card is used.
 *
 * @author Deep Patel
 * @version 2.0
 */
public class NegotiateOrder extends Order {
    private final Player d_player1;
    private final Player d_player2;

    /**
     * To find the player using its data members.
     */
    private final PlayerRepository d_playerRepository = new PlayerRepository();

    private final LogEntryBuffer d_logEntryBuffer = LogEntryBuffer.getLogger();

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
     *
     * @throws CardNotFoundException Card doesn't found in the player's card list.
     */
    @Override
    public void execute() throws CardNotFoundException {
        StringBuilder l_logResponse = new StringBuilder();
        l_logResponse.append("\n" + "Executing " + d_player1.getName() + " Order:" + "\n");
        // Get diplomacy card.
        Card l_requiredCard = d_player1.getCard(CardType.DIPLOMACY);
        d_player1.addNegotiatePlayer(d_player2);
        d_player2.addNegotiatePlayer(d_player1);
        d_player1.removeCard(l_requiredCard);
        l_logResponse.append("\n Order Effect\n" + "Negotiating between " + d_player1.getName() + " and " + d_player2.getName() + "\n");
        d_logEntryBuffer.dataChanged("negotiate", l_logResponse.toString());
    }

    /**
     * Gets the type of order.
     *
     * @return Value of the order type.
     */
    public OrderType getType() {
        return OrderType.negotiate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void expire() {
        d_player1.removeNegotiatePlayer(d_player2);
        d_player2.removeNegotiatePlayer(d_player1);
    }

    /**
     * Returns the string describing player order.
     *
     * @return String representing player orders.
     */
    @Override
    public String toString() {
        return String.format("%s %s", getType().getJsonValue(), d_player2.getName());
    }
}
