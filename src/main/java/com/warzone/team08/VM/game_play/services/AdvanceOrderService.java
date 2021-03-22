package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.common.services.AssignRandomCardService;
import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.repositories.CountryRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.Math.round;

/**
 * This class implements operations performed when advance order is executed.
 * Advance order moves the armies from source country to destination country.
 * <ul>
 *  <li>
 *      If the source and destination country is owned by the same player then it simply moves armies
 *      and performs addition operation on the destination country.
 *  </li>
 *  <li>
 *      If the source and destination country is not owned by the same player then the Battle will occur.
 *  </li>
 * </ul>
 *
 * @author CHARIT
 */
public class AdvanceOrderService implements Order {

    private String d_countryFrom;
    private String d_countryTo;
    private int d_numOfArmies;
    private Player d_player;

    /**
     * Sets the values of data members.
     *
     * @param p_countryFrom Source country name.
     * @param p_countryTo   Destination country name.
     * @param p_numOfArmies Number of armies to be moved.
     * @param p_player      Current player object.
     */
    public AdvanceOrderService(String p_countryFrom, String p_countryTo, int p_numOfArmies, Player p_player) {
        d_countryFrom = p_countryFrom;
        d_countryTo = p_countryTo;
        d_numOfArmies = p_numOfArmies;
        d_player = p_player;
    }

    /**
     * Perform actual advance operation.
     *
     * @throws EntityNotFoundException Throws if the country name doesn't exist.
     * @throws InvalidInputException   Throws if the Input is invalid.
     */
    public void execute() throws EntityNotFoundException, InvalidInputException {
        Country l_countryFrom;
        Country l_countryTo;
        List<Country> l_assignCountryList;
        List<Country> l_neighborCountryList;
        if (isNotNegotiation()) {
            //Handles invalid country name(Country doesn't exists)
            l_countryFrom = new CountryRepository().findFirstByCountryName(d_countryFrom);
            l_countryTo = new CountryRepository().findFirstByCountryName(d_countryTo);
            l_assignCountryList = d_player.getAssignedCountries();
            l_neighborCountryList = l_countryFrom.getNeighbourCountries();

            //Checks the source country is owned by a current player or not. If not then throws an exception.
            if (!l_assignCountryList.contains(l_countryFrom)) {
                throw new InvalidInputException("Please select your own country as a source country.");
            }

            //Checks if the number of moved armies is less than zero.
            if (d_numOfArmies < 0) {
                throw new InvalidInputException("Number of armies can not be negative.");
            }

            //Checks whether the destination country is the neighbor country of the source country or not. If not then throws an exception.
            if (!l_neighborCountryList.contains(l_countryTo)) {
                throw new InvalidInputException("Please select any of the neighbor country of the source country as a destination country as we can perform Advance order on neighbor countries only.");
            }

            //If destination country is owned by the current player then it simply moves armies to the destination country.
            if (l_assignCountryList.contains(l_countryTo)) {
                //move armies and add
                int l_remainingArmies = l_countryFrom.getNumberOfArmies() - d_numOfArmies;
                if (l_remainingArmies < 0) {
                    //throw new InvalidInputException("Insufficient armies");
                    d_numOfArmies = l_countryFrom.getNumberOfArmies();
                    l_remainingArmies = 0;
                }
                l_countryFrom.setNumberOfArmies(l_remainingArmies);
                l_countryTo.setNumberOfArmies(l_countryTo.getNumberOfArmies() + d_numOfArmies);
            }
            //If destination country is not owned by the current player than it performs battle.
            else {
                //move armies and battle
                int l_defendingArmies = l_countryTo.getNumberOfArmies();
                int l_attackingArmies = d_numOfArmies;
                int l_remainingArmies = l_countryFrom.getNumberOfArmies() - d_numOfArmies;
                if (l_remainingArmies < 0) {
                    l_attackingArmies = l_countryFrom.getNumberOfArmies();
                    l_remainingArmies = 0;
                }
                l_countryFrom.setNumberOfArmies(l_remainingArmies);

                int l_attackersKilled = (int) round(l_defendingArmies * 0.7);
                int l_defendersKilled = (int) round(l_attackingArmies * 0.6);

                if (l_defendersKilled >= l_defendingArmies) {
                    Player l_owner = l_countryTo.getOwnedBy();
                    l_owner.removeCountry(l_countryTo);

                    //owner changed.
                    l_countryTo.setOwnedBy(d_player);
                    List<Country> l_assign = d_player.getAssignedCountries();
                    l_assign.add(l_countryTo);
                    d_player.setAssignedCountries(l_assign);
                    l_countryTo.setNumberOfArmies(l_attackingArmies - l_attackersKilled);

                    d_player.addCard(AssignRandomCardService.randomCard());
                } else {
                    l_countryFrom.setNumberOfArmies(l_countryFrom.getNumberOfArmies() + l_attackingArmies - l_attackersKilled);
                    l_countryTo.setNumberOfArmies(l_defendingArmies - l_defendersKilled);
                }
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
        return OrderType.advance;
    }

    /**
     * This method checks that player has not negotiated with the attacked country owner.
     *
     * @return True if no negotiaton
     * @throws EntityNotFoundException not able to find the Owner.
     */
    public boolean isNotNegotiation() throws EntityNotFoundException {
        List<Player> l_negotiationPlayer = d_player.getNegotiationplayers();
        if (l_negotiationPlayer == null) {
            return true;
        } else {
            Player l_player2 = this.getCountryTo().getOwnedBy();
            for (Player l_loopPlayer : l_negotiationPlayer) {
                if (l_loopPlayer.equals(l_player2)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns country object of CountyTo.
     *
     * @return Attacked country object.
     * @throws EntityNotFoundException Throws if the given country is not found in the list of available countries.
     */
    public Country getCountryTo() throws EntityNotFoundException {
        return new CountryRepository().findFirstByCountryName(d_countryTo);
    }
}