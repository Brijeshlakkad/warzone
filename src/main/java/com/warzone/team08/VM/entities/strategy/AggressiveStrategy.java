package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.CardType;
import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.entities.orders.*;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;

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
    private Country d_cardCountry;
    private BombOrder d_bomb;
    private AirliftOrder d_airlift;
    private BlockadeOrder d_blockade;
    private NegotiateOrder d_negotiate;

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
     * This method is useful for craete orders of the cards
     *
     * @throws EntityNotFoundException  throws If not found
     * @throws InvalidArgumentException throws If enter invalid input
     */
    public void cards() throws EntityNotFoundException, InvalidArgumentException {
        int counter = 1;
        if (d_player.hasCard(CardType.BOMB)) {
            d_cardCountry = d_oppositionsCountry;
            d_bomb = new BombOrder(d_cardCountry.getCountryName(), d_player);
            counter--;
        }
        if (counter != 0 && d_player.hasCard(CardType.BLOCKADE)) {
            int count = 0;
            for (Country l_traverseCountry : d_attackingCountry.getNeighbourCountries()) {
                if (!l_traverseCountry.getOwnedBy().equals(d_player) && l_traverseCountry.getNumberOfArmies() > count) {
                    count = l_traverseCountry.getNumberOfArmies();
                    d_cardCountry = l_traverseCountry;
                }
            }
            d_blockade = new BlockadeOrder(d_cardCountry.getCountryName(), d_player);
            counter--;
        }
        if (counter != 0 && d_player.hasCard(CardType.AIRLIFT)) {
            int count = 0;
            for (Country c : d_ownedCountries) {
                if (c.getNumberOfArmies() > count && !c.equals(d_attackingCountry)) {
                    count = c.getNumberOfArmies();
                    d_cardCountry = c;
                }
            }
            d_airlift = new AirliftOrder(d_cardCountry.getCountryName(), d_attackingCountry.getCountryName(), String.valueOf(d_cardCountry.getNumberOfArmies() - 1), d_player);
            counter--;
        }
        if (counter != 0 && d_player.hasCard(CardType.DIPLOMACY)) {
            for (Country l_traverseCountry : d_attackingCountry.getNeighbourCountries()) {
                if (!l_traverseCountry.getOwnedBy().equals(d_player)) {
                    d_cardCountry = l_traverseCountry;
                    break;
                }
            }
            d_negotiate = new NegotiateOrder(d_player, d_cardCountry.getOwnedBy().toString());
            counter--;
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
    public void execute() throws InvalidArgumentException, EntityNotFoundException {
        deploy();
        opposition();
        cards();
        DeployOrder l_deployOrder = new DeployOrder(d_attackingCountry.getCountryName(), String.valueOf(d_player.getReinforcementCount()), d_player);
        this.d_player.addOrder(l_deployOrder);

        int counter = 1;
        if (d_player.hasCard(CardType.BOMB)) {
            this.d_player.addOrder(d_bomb);
            counter--;
        }
        if (d_player.hasCard(CardType.AIRLIFT) && counter == 1) {
            this.d_player.addOrder(d_airlift);
            counter--;
        }
        if (d_player.hasCard(CardType.BLOCKADE) && counter == 1) {
            this.d_player.addOrder(d_blockade);
            counter--;
        }
        if (d_player.hasCard(CardType.DIPLOMACY) && counter == 1) {
            this.d_player.addOrder(d_negotiate);
            counter--;
        }
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
