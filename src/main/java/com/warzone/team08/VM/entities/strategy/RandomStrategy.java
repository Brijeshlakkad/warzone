package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.entities.orders.AdvanceOrder;
import com.warzone.team08.VM.entities.orders.DeployOrder;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.Random;

/**
 * This class defines the behavior of random player.
 *
 * @author Deep Patel
 * @author Brijesh Lakkad
 */
public class RandomStrategy extends PlayerStrategy {

    private List<Country> d_ownedCountries;
    private Country d_attackingCountry;
    private Country d_oppositionCountry;
    Random d_random =new Random();

    public RandomStrategy(Player p_player) {
        super(p_player);
    }

    public Country getAttackingCountry() {
        return d_attackingCountry;
    }

    public void setAttackingCountry(Country p_attackingCountry) {
        d_attackingCountry = p_attackingCountry;
    }

    public Country getOppositionCountry() {
        return d_oppositionCountry;
    }

    public void setOppositionCountry(Country p_oppositionCountry) {
        d_oppositionCountry = p_oppositionCountry;
    }

    public void deploy(){
        d_ownedCountries=d_player.getAssignedCountries();
        d_attackingCountry = d_ownedCountries.get(d_random.nextInt(d_ownedCountries.size()));
    }

    public void opposition(){
        List<Country> l_neighborCountries= d_attackingCountry.getNeighbourCountries();
        d_oppositionCountry=l_neighborCountries.get(d_random.nextInt(l_neighborCountries.size()));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws InvalidArgumentException, EntityNotFoundException {
        deploy();
        opposition();
        DeployOrder l_deployOrder=new DeployOrder(d_attackingCountry.getCountryName(),String.valueOf(d_player.getReinforcementCount()),d_player);
        this.d_player.addOrder(l_deployOrder);
        int l_advanceArmy = d_attackingCountry.getNumberOfArmies() + d_player.getRemainingReinforcementCount();
        AdvanceOrder l_advanceOrder=new AdvanceOrder(d_attackingCountry.getCountryName(),d_oppositionCountry.getCountryName(),String.valueOf(l_advanceArmy),d_player);
        this.d_player.addOrder(l_advanceOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrategyType getType() {
        return StrategyType.RANDOM;
    }
}
