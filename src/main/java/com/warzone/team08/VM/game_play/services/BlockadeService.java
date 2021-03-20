package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.exceptions.InvalidCommandException;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.repositories.CountryRepository;

import java.util.List;

/**
 * This class implements the operations required to be perform when the Blockade card is used.
 *
 * @author CHARIT
 * @version 2.0
 */
public class BlockadeService implements Order {
    private String d_countryId;
    private Player d_player;
    public static final int CONSTANT = 3;

    /**
     * Sets the country name and current player object.
     *
     * @param p_countryId Country name.
     * @param p_player    Current player object.
     */
    public BlockadeService(String p_countryId, Player p_player) {
        d_countryId = String.valueOf(p_countryId);
        d_player = p_player;
    }

    /**
     * Returns country object on which the bomb operation is performed.
     *
     * @return Attacked country object.
     * @throws EntityNotFoundException Throws if the given country is not found in the list of available countries.
     */
    public Country getAttackedCountry() throws EntityNotFoundException {
        return new CountryRepository().findFirstByCountryName(d_countryId);
    }

    /**
     * Checks whether the given command is executable or not(valid or not).
     *
     * @return True if the command is valid, false otherwise.
     * @throws EntityNotFoundException  Throws if the given country is not found in the list of available countries.
     * @throws InvalidCommandException  Throws if the command is invalid.
     * @throws InvalidInputException    Throws in case of invalid input.
     */
    public boolean valid() throws EntityNotFoundException, InvalidCommandException, InvalidInputException {
        if (this.getAttackedCountry().getOwnedBy() == d_player) {
            if (d_player.getCards().contains("blockade")) {
                return true;
            } else {
                throw new InvalidCommandException("Blockade card is not available with the player.");
            }
        } else {
            throw new InvalidInputException("You have selected opponent player's country to perform blockade operation. Please your own country");
        }
    }

    /**
     * Performs actual blockade operation.
     *
     * @throws InvalidInputException    Throws if user input is invalid.
     * @throws EntityNotFoundException  Throws if the given country is not found in the list of available countries.
     * @throws InvalidCommandException  Throws if the command is invalid.
     * @throws InvalidArgumentException Throws if the argument value in the command is invalid.
     */
    public void execute() throws EntityNotFoundException, InvalidInputException, InvalidCommandException, InvalidArgumentException {
        Country l_country;
        List<Country> l_countryList;
        if (valid())
        {
            l_country = this.getAttackedCountry();
            l_countryList = d_player.getAssignedCountries();
            try{
                l_country.setNumberOfArmies(l_country.getNumberOfArmies() * CONSTANT);
                l_countryList.remove(l_country);
            }
            catch (Exception e)
            {
                throw new InvalidArgumentException("You can not perform blockade operation as you don't own this country");
            }
            d_player.setAssignedCountries(l_countryList);
            d_player.removeCard("blockade");
        }
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
