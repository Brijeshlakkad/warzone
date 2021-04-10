package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.entities.orders.AdvanceOrder;
import com.warzone.team08.VM.entities.orders.DeployOrder;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.exceptions.InvalidOrderException;

import java.util.List;


/**
 * This class defines the behavior of aggressive player.
 *
 * @author Deep Patel
 * @author Brijesh Lakkad
 */
public class AggressiveStrategy extends PlayerStrategy {

    private List<Country> d_ownedCountries;
    private Country d_attackingCountry;
    private Country d_oppositionsCountry;

    /**
     * caling super class constructor
     *
     * @param p_player defines player
     */
    public AggressiveStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * This method finds the strongest country of the aggressive player.
     */
    public void deploy() {
        d_ownedCountries = d_player.getAssignedCountries();
        int count = 0;
        for (Country c : d_ownedCountries) {
            if (c.getNumberOfArmies() > count) {
                count = c.getNumberOfArmies();
                d_attackingCountry = c;
            }
        }
    }

    /**
     * This method finds opposite player's country, Which is neighbour of the deploy Country.
     */
    public void opposition() {
        for (Country l_traverseCountry : d_attackingCountry.getNeighbourCountries()) {
            if (!l_traverseCountry.getOwnedBy().equals(d_player)) {
                d_oppositionsCountry = l_traverseCountry;
                break;
            }
        }
    }

    /**
     * getter method for the country from which countries deploy
     *
     * @return the Country
     */
    public Country getDeployCountry() {
        return d_attackingCountry;
    }

    /**
     * getter method for fetch defending country
     *
     * @return the country
     */
    public Country getOppositionCountry() {
        return d_oppositionsCountry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws InvalidArgumentException, EntityNotFoundException, InvalidOrderException {
        deploy();
        opposition();
        DeployOrder l_deployOrder = new DeployOrder(d_attackingCountry.getCountryName(), String.valueOf(d_player.getReinforcementCount()), d_player);
        this.d_player.addOrder(l_deployOrder);
        int l_advanceArmy = d_attackingCountry.getNumberOfArmies() + d_player.getRemainingReinforcementCount();
        AdvanceOrder l_advanceOrder = new AdvanceOrder(d_attackingCountry.getCountryName(), d_oppositionsCountry.getCountryName(), String.valueOf(l_advanceArmy - 1), d_player);
        this.d_player.addOrder(l_advanceOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrategyType getType() {
        return StrategyType.AGGRESSIVE;
    }
}