package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.repositories.CountryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the behavior of cheater player.
 *
 * @author Deep Patel
 * @author Brijesh Lakkad
 */
public class CheaterStrategy extends PlayerStrategy {
    private List<Country> d_ownedCountries;
    private List<Country> d_cheatCountry;

    public CheaterStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * This method transfer ownership of all the neighbour enemy countries to the cheater player.
     *
     * @throws EntityNotFoundException Throws if not able to find Country.
     */
    public void cheating() throws EntityNotFoundException {
        List<String> a = new ArrayList<>();
        CountryRepository l_repository = new CountryRepository();
        Country l_transferOwner;
        d_ownedCountries = d_player.getAssignedCountries();
        for (Country l_travers : d_ownedCountries) {
            for (Country l_neighbour : l_travers.getNeighbourCountries()) {
                if (!l_neighbour.getOwnedBy().equals(d_player)) {
                    l_neighbour.setOwnedBy(d_player);
                    a.add(l_neighbour.getCountryName());
                }
            }
        }
        //add countries to the cheater player
        for (String l_countryName : a) {
            l_transferOwner = l_repository.findFirstByCountryName(l_countryName);
            d_player.addAssignedCountries(l_transferOwner);
        }
    }

    /**
     * This function doubles the cheater player army which has enemy neighbour
     */
    public void doublearmy() {
        d_cheatCountry = d_player.getAssignedCountries();
        for (Country l_travers : d_ownedCountries) {
            for (Country l_neighbour : l_travers.getNeighbourCountries()) {
                if (!l_neighbour.getOwnedBy().equals(d_player)) {
                    l_travers.setNumberOfArmies(l_travers.getNumberOfArmies() * 2);
                    break;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws EntityNotFoundException {
        cheating();
        doublearmy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrategyType getType() {
        return StrategyType.CHEATER;
    }
}
