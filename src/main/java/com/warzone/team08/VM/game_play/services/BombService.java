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

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;

/**
 * This class implements the operations required to be perform when the Bomb card is used.
 * When bomb card is used then it simple halves the number of armies available in the country on which the bomb operation is performed.
 * A player can perform bomb operation on the countries owned by the other players only.
 *
 * @author CHARIT
 * @version 2.0
 */
public class BombService implements Order {

    private String d_countryId;
    private Player d_player;

    /**
     * Sets the country name and current player object.
     *
     * @param p_countryId Country name.
     * @param p_player    Current player object.
     */
    public BombService(String p_countryId, Player p_player) {
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
        if (this.getAttackedCountry().getOwnedBy() != d_player) {
            if (d_player.getCards().contains("bomb")) {
                return true;
            } else {
                throw new InvalidCommandException("Bomb card is not available with the player.");
            }
        } else {
            throw new InvalidInputException("You have selected your own country to perform bomb operation. Please select opponent player's country");
        }
    }

    /**
     * Performs actual bomb operation.
     *
     * @throws InvalidInputException    Throws if user input is invalid.
     * @throws EntityNotFoundException  Throws if the given country is not found in the list of available countries.
     * @throws InvalidCommandException  Throws if the command is invalid.
     */
    public void execute() throws InvalidInputException, EntityNotFoundException, InvalidCommandException {
        Country l_country;
        List<Country> l_countryList;
        if (valid()) {
            try {
                List<Country> l_neighbourCountryList = new ArrayList<>();
                l_country = this.getAttackedCountry();
                l_countryList = d_player.getAssignedCountries();
                for (Country l_co : l_countryList) {
                    l_neighbourCountryList.addAll(l_co.getNeighbourCountries());
                }
                if (l_neighbourCountryList.contains(l_country)) {
                    int l_finalArmies = (int) floor(l_country.getNumberOfArmies() / 2);
                    l_country.setNumberOfArmies(l_finalArmies);
                    //Remove card from list
                    d_player.removeCard("bomb");
                } else {
                    throw new InvalidArgumentException("Invalid Country Name is provided!! Country must be a neighboring country.");
                }
            } catch (Exception e) {
                throw new InvalidInputException("CountryId is not in valid format. It must be a String value");
            }
        }
    }

    /**
     * Returns the order type.
     *
     * @return Order type.
     */
    @Override
    public OrderType getType() {
        return OrderType.bomb;
    }
}
