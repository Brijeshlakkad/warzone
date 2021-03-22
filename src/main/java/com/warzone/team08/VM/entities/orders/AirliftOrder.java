package com.warzone.team08.VM.entities.orders;

import com.warzone.team08.CLI.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidOrderException;
import com.warzone.team08.VM.repositories.CountryRepository;

/**
 * This class implements the operations required to be performed when the Airlift card is used.
 *
 * @author Deep Patel
 * @version 2.0
 */
public class AirliftOrder implements Order {
    private final Country d_sourceCountry;
    private final Country d_targetCountry;
    private final int d_numOfArmies;
    private Player d_owner;

    /**
     * To find the country using its data members.
     */
    private final CountryRepository d_countryRepository = new CountryRepository();

    /**
     * sets the source and target country id along with number of armies to be airlifted and player object.
     *
     * @param p_sourceCountry source country id from which armies will be airlifted
     * @param p_targetCountry target country id where armies will be moved.
     * @param p_numOfArmies   number of armies for airlift
     * @param p_owner         current player object
     * @throws EntityNotFoundException  Throws if the country with the given name doesn't exist.
     * @throws InvalidArgumentException Throws if the input is invalid.
     */
    public AirliftOrder(String p_sourceCountry, String p_targetCountry, String p_numOfArmies, Player p_owner)
            throws
            EntityNotFoundException,
            InvalidArgumentException {
        d_sourceCountry = d_countryRepository.findFirstByCountryName(p_sourceCountry);
        d_targetCountry = d_countryRepository.findFirstByCountryName(p_targetCountry);
        try {
            d_numOfArmies = Integer.parseInt(p_numOfArmies);
        } catch (NumberFormatException p_e) {
            throw new InvalidArgumentException("Number of reinforcements is not a number!");
        }
        d_owner = p_owner;
    }

    /**
     * Gets the type of order.
     *
     * @return Value of the order type.
     */
    @Override
    public OrderType getType() {
        return OrderType.airlift;
    }

    /**
     * Performs the airlift operation by transferring armies.
     *
     * @throws InvalidOrderException If the order can not be performed due to an invalid country, an invalid number of
     *                               armies, or other invalid input.
     */
    @Override
    public void execute() throws InvalidOrderException {
        // Verify that all the conditions has been fulfilled for the airlift command.
        if (d_sourceCountry.getOwnedBy() == d_owner && d_targetCountry.getOwnedBy() == d_owner) {
            if (d_owner.getCards().contains("airlift")) {
                if (d_sourceCountry.getNumberOfArmies() < d_numOfArmies) {
                    throw new InvalidOrderException("Source country not have entered amount of armies for airlift!");
                }
            } else {
                throw new InvalidOrderException("Airlift card is not available with the player!");
            }
        } else {
            throw new InvalidOrderException("You have to select source and target country both from your owned countries!");
        }

        int l_sourceCountryArmies = d_sourceCountry.getNumberOfArmies();
        int l_targetCountryArmies = d_targetCountry.getNumberOfArmies();
        l_sourceCountryArmies -= d_numOfArmies;
        l_targetCountryArmies += d_numOfArmies;
        d_sourceCountry.setNumberOfArmies(l_sourceCountryArmies);
        d_targetCountry.setNumberOfArmies(l_targetCountryArmies);
        d_owner.removeCard("airlift");
    }
}
