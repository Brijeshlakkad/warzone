package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.CardType;
import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.constants.interfaces.Card;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.entities.orders.*;
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
    Random d_random = new Random();
    private BombOrder d_bomb;
    private AirliftOrder d_airlift;
    private BlockadeOrder d_blockade;
    private NegotiateOrder d_negotiate;

    /**
     * set the player
     *
     * @param p_player defines Plater
     */
    public RandomStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * Find out attacking country
     *
     * @return the Country
     */
    public Country getAttackingCountry() {
        return d_attackingCountry;
    }

    /**
     * setter method
     *
     * @param p_attackingCountry is the attacking country
     */
    public void setAttackingCountry(Country p_attackingCountry) {
        d_attackingCountry = p_attackingCountry;
    }

    /**
     * getter method
     *
     * @return the opposition country
     */
    public Country getOppositionCountry() {
        return d_oppositionCountry;
    }

    /**
     * setter method for Opposition country
     *
     * @param p_oppositionCountry contains opposition country
     */
    public void setOppositionCountry(Country p_oppositionCountry) {
        d_oppositionCountry = p_oppositionCountry;
    }

    /**
     * This method finds the strongest country of the aggressive player.
     */
    public void deploy() {
        d_ownedCountries = d_player.getAssignedCountries();
        d_attackingCountry = d_ownedCountries.get(d_random.nextInt(d_ownedCountries.size()));
    }

    /**
     * This method finds opposite player's country, Which is neighbour of the deploy Country.
     */
    public void opposition() {
        List<Country> l_neighborCountries = d_attackingCountry.getNeighbourCountries();
        d_oppositionCountry = l_neighborCountries.get(d_random.nextInt(l_neighborCountries.size()));
    }

    /**
     * This method is useful for create orders of the cards
     *
     * @param p_card Card to be created.
     * @throws EntityNotFoundException  throws If not found
     * @throws InvalidArgumentException throws If enter invalid input
     */
    public void cards(Card p_card) throws EntityNotFoundException, InvalidArgumentException {
        if (p_card.getType() == CardType.BOMB) {
            d_bomb = new BombOrder(d_oppositionCountry.getCountryName(), d_player);
            this.d_player.addOrder(d_bomb);
        }
        if (p_card.getType() == CardType.AIRLIFT) {
            Country d_targetCountry = d_ownedCountries.get(d_random.nextInt(d_ownedCountries.size()));
            d_airlift = new AirliftOrder(d_attackingCountry.getCountryName(), d_targetCountry.getCountryName(), String.valueOf(d_attackingCountry.getNumberOfArmies() - 1), d_player);
            this.d_player.addOrder(d_airlift);
        }
        if (p_card.getType() == CardType.BLOCKADE) {
            d_blockade = new BlockadeOrder(d_oppositionCountry.getCountryName(), d_player);
            this.d_player.addOrder(d_blockade);
        }
        if (p_card.getType() == CardType.DIPLOMACY) {
            d_negotiate = new NegotiateOrder(d_player, d_oppositionCountry.getOwnedBy().toString());
            this.d_player.addOrder(d_negotiate);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws InvalidArgumentException, EntityNotFoundException {
        deploy();
        opposition();
        DeployOrder l_deployOrder = new DeployOrder(d_attackingCountry.getCountryName(), String.valueOf(d_player.getReinforcementCount()), d_player);
        this.d_player.addOrder(l_deployOrder);
        if (d_player.hasCard(CardType.BOMB) || d_player.hasCard(CardType.AIRLIFT) || d_player.hasCard(CardType.DIPLOMACY) || d_player.hasCard(CardType.BLOCKADE)) {
            Card l_card = d_player.getCards().get(d_random.nextInt(d_player.getCards().size()));
            cards(l_card);
        }
        int l_advanceArmy = d_attackingCountry.getNumberOfArmies() + d_player.getRemainingReinforcementCount();
        AdvanceOrder l_advanceOrder = new AdvanceOrder(d_attackingCountry.getCountryName(), d_oppositionCountry.getCountryName(), String.valueOf(l_advanceArmy), d_player);
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
