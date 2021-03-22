package com.warzone.team08.VM.entities.orders;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidOrderException;
import com.warzone.team08.VM.repositories.CountryRepository;

import java.util.List;

/**
 * This class implements the operations required to be perform when the Blockade card is used. When blockade card is
 * used then it simply multiplies the number of armies by certain constant value and make that country a neutral
 * country. A player can perform blockade operation on its own country.
 *
 * @author CHARIT
 * @version 2.0
 */
public class BlockadeOrder implements Order {
    private final Country d_targetCountry;
    private final Player d_owner;
    public static final int CONSTANT = 3;

    /**
     * To find the country using its data members.
     */
    private final CountryRepository d_countryRepository = new CountryRepository();

    /**
     * Sets the country name and current player object.
     *
     * @param p_targetCountry Country name.
     * @param p_owner         Player who has initiated this order.
     * @throws EntityNotFoundException Throws if the given country is not found in the list of available countries.
     */
    public BlockadeOrder(String p_targetCountry, Player p_owner)
            throws
            EntityNotFoundException {
        d_targetCountry = d_countryRepository.findFirstByCountryName(p_targetCountry);
        d_owner = p_owner;
    }

    /**
     * Performs actual blockade operation.
     *
     * @throws InvalidOrderException If the order can not be performed due to an invalid country, an invalid number * of
     *                               armies, or other invalid input.
     */
    public void execute() throws InvalidOrderException {
        Country l_country;
        List<Country> l_countryList;
        if (d_targetCountry.getOwnedBy() == d_owner) {
            if (!d_owner.getCards().contains("blockade")) {
                throw new InvalidOrderException("Blockade card is not available with the player.");
            }
        } else {
            throw new InvalidOrderException("You have selected opponent player's country to perform blockade operation.");
        }

        l_country = d_targetCountry;
        l_countryList = d_owner.getAssignedCountries();
        try {
            l_country.setNumberOfArmies(l_country.getNumberOfArmies() * CONSTANT);
            l_countryList.remove(l_country);
        } catch (Exception e) {
            throw new InvalidOrderException("You can not perform blockade operation as you don't own this country");
        }
        d_owner.setAssignedCountries(l_countryList);
        d_owner.removeCard("blockade");
    }

    /**
     * Returns the order type.
     *
     * @return Order type.
     */
    @Override
    public OrderType getType() {
        return OrderType.blockade;
    }
}
