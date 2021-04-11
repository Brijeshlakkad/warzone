package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.entities.orders.AdvanceOrder;
import com.warzone.team08.VM.entities.orders.DeployOrder;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;

import java.lang.reflect.Array;
import java.util.List;

/**
 * This class defines the behavior of benevolent player.
 *
 * @author Deep Patel
 * @author Brijesh Lakkad
 */
public class BenevolentStrategy extends PlayerStrategy {

    private List<Country> d_ownedCountries;
    private int[] d_temValue =new int[d_player.getAssignedCountries().size()];
    private int[] d_countriesArmyValues = new int[d_player.getAssignedCountries().size()];

    public BenevolentStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * This method finds the strongest country of the aggressive player.
     */
    public void deploy()
    {
        d_ownedCountries = d_player.getAssignedCountries();

        //set the integer arrays, which contains number of armies for the respective country
        for (int i=0;i<d_ownedCountries.size();i++) {
            d_ownedCountries.get(i).setNumberOfArmies(d_ownedCountries.get(i).getNumberOfArmies());
            d_countriesArmyValues[i]= d_ownedCountries.get(i).getNumberOfArmies();
            d_temValue[i]= d_ownedCountries.get(i).getNumberOfArmies();
        }

        // deploy order logic
        Country l_currentAttacking = d_ownedCountries.get(0);
        for (int j = 0; j < d_player.getReinforcementCount(); j++) {
            for (Country l_traverse : d_ownedCountries) {
                if(l_traverse.getNumberOfArmies()<l_currentAttacking.getNumberOfArmies())
                {
                    l_currentAttacking = l_traverse;
                }
            }
            l_currentAttacking.setNumberOfArmies(l_currentAttacking.getNumberOfArmies()+1);
        }

        //set back for the orders
        for (int i=0;i<d_ownedCountries.size();i++) {
            d_countriesArmyValues[i]= d_ownedCountries.get(i).getNumberOfArmies();
            d_ownedCountries.get(i).setNumberOfArmies(d_temValue[i]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws InvalidArgumentException, EntityNotFoundException {
        deploy();
        for(int i=0;i<d_ownedCountries.size();i++)
        {
            DeployOrder l_deployOrder = new DeployOrder(d_ownedCountries.get(i).getCountryName(), String.valueOf(d_countriesArmyValues[i] - d_ownedCountries.get(i).getNumberOfArmies()), d_player);
            this.d_player.addOrder(l_deployOrder);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrategyType getType() {
        return StrategyType.BENEVOLENT;
    }
}