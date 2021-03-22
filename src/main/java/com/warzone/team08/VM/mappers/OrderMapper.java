package com.warzone.team08.VM.mappers;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.entities.orders.*;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.exceptions.InvalidCommandException;
import com.warzone.team08.VM.responses.CommandResponse;

/**
 * The class to map <code>CommandResponse</code> to <code>Order</code>.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class OrderMapper {
    /**
     * Maps the <code>CommandResponse</code> to <code>Order</code> object.
     * <p>This is a static method and returns a new
     * order instance created from <code>CommandResponse</code>.
     *
     * @param p_commandResponse Command object received at VM which represents the user command input.
     * @param p_player          Player who has issued the order.
     * @return Value of the new order created from user command response.
     * @throws EntityNotFoundException  Throws if the target entity not found.
     * @throws InvalidCommandException  Throws if the required command values are not present.
     * @throws InvalidArgumentException Throws if the argument can not be converted to the required type.
     */
    public Order toOrder(CommandResponse p_commandResponse, Player p_player)
            throws EntityNotFoundException,
            InvalidCommandException,
            InvalidArgumentException {
        OrderType l_orderType = OrderType.valueOf(p_commandResponse.getHeadCommand().toLowerCase());
        try {
            if (l_orderType == OrderType.advance) {
                return new AdvanceOrder(p_commandResponse.getCommandValues().get(0), p_commandResponse.getCommandValues().get(1), p_commandResponse.getCommandValues().get(2), p_player);
            } else if (l_orderType == OrderType.airlift) {
                return new AirliftOrder(p_commandResponse.getCommandValues().get(0), p_commandResponse.getCommandValues().get(1), p_commandResponse.getCommandValues().get(2), p_player);
            } else if (l_orderType == OrderType.blockade) {
                return new BlockadeOrder(p_commandResponse.getCommandValues().get(0), p_player);
            } else if (l_orderType == OrderType.bomb) {
                return new BombOrder(p_commandResponse.getCommandValues().get(0), p_player);
            } else if (l_orderType == OrderType.deploy) {
                return new DeployOrder(p_commandResponse.getCommandValues().get(0), p_commandResponse.getCommandValues().get(1), p_player);
            } else if (l_orderType == OrderType.negotiate) {
                return new NegotiateOrder(p_player, p_commandResponse.getCommandValues().get(0));
            }
            // If not known order, throws an exception.
        } catch (ArrayIndexOutOfBoundsException p_e) {
            // If not handled here, it will throw a InvalidCommandException.
        }
        throw new InvalidCommandException("Invalid command!");
    }
}
