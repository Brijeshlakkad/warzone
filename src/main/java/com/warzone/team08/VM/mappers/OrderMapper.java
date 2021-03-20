package com.warzone.team08.VM.mappers;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.DeployOrder;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.exceptions.InvalidCommandException;
import com.warzone.team08.VM.repositories.CountryRepository;
import com.warzone.team08.VM.responses.CommandResponse;

/**
 * The class to map <code>CommandResponse</code> to <code>Order</code>.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class OrderMapper {
    /**
     * To find the country given its name.
     */
    private final CountryRepository COUNTRY_REPOSITORY = new CountryRepository();

    /**
     * Maps the <code>CommandResponse</code> to <code>Order</code> object.
     * <p>This is a static method and returns a new
     * order instance created from <code>CommandResponse</code>.
     *
     * @param p_commandResponse Command object received at VM which represents the user command input.
     * @param p_player          Player who has issued the order.
     * @return Value of the new order created from user command response.
     * @throws EntityNotFoundException  Throws if the target country not found.
     * @throws InvalidCommandException  Throws if the command values not present.
     * @throws InvalidArgumentException Throws if the number of reinforcements is not a number.
     */
    public Order toOrder(CommandResponse p_commandResponse, Player p_player)
            throws EntityNotFoundException,
            InvalidCommandException,
            InvalidArgumentException {
        OrderType l_orderType = OrderType.valueOf(p_commandResponse.getHeadCommand().toLowerCase());
        if (l_orderType == OrderType.deploy) {
            try {
                // Get the target country from repository.
                Country l_targetCountry = COUNTRY_REPOSITORY.findFirstByCountryName(p_commandResponse.getCommandValues().get(0));
                int l_numberOfReinforcement;
                try {
                    l_numberOfReinforcement = Integer.parseInt(p_commandResponse.getCommandValues().get(1));
                } catch (NumberFormatException p_e) {
                    throw new InvalidArgumentException("Number of reinforcements is not a number!");
                }
                return new DeployOrder(p_player, l_targetCountry, l_numberOfReinforcement);
            } catch (ArrayIndexOutOfBoundsException p_e) {
                // If not handled here, it will throw a InvalidCommandException.
            }
        }
        throw new InvalidCommandException("Invalid command!");
    }
}
