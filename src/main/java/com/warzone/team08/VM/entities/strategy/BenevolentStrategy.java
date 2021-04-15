package com.warzone.team08.VM.entities.strategy;

import com.warzone.team08.VM.constants.enums.CardType;
import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.entities.orders.AirliftOrder;
import com.warzone.team08.VM.entities.orders.BlockadeOrder;
import com.warzone.team08.VM.entities.orders.DeployOrder;
import com.warzone.team08.VM.entities.orders.NegotiateOrder;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.logger.LogEntryBuffer;

import java.util.List;

/**
 * This class defines the behavior of benevolent player.
 *
 * @author Deep Patel
 * @author Brijesh Lakkad
 */
public class BenevolentStrategy extends PlayerStrategy {
    private List<Country> d_ownedCountries;
    private int[] d_temValue = new int[d_player.getAssignedCountries().size()];
    private int[] d_countriesArmyValues = new int[d_player.getAssignedCountries().size()];
    private Country d_cardCountry;
    private Country d_target;
    private NegotiateOrder d_negotiate;
    private BlockadeOrder d_blockade;
    private AirliftOrder d_airlift;
    private final LogEntryBuffer d_logEntryBuffer = LogEntryBuffer.getLogger();

    public BenevolentStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * This method sets the number of armies to deploy.
     */
    public void deploy() {
        d_ownedCountries = d_player.getAssignedCountries();

        //set the integer arrays, which contains number of armies for the respective country
        for (int i = 0; i < d_ownedCountries.size(); i++) {
            d_ownedCountries.get(i).setNumberOfArmies(d_ownedCountries.get(i).getNumberOfArmies());
            d_countriesArmyValues[i] = d_ownedCountries.get(i).getNumberOfArmies();
            d_temValue[i] = d_ownedCountries.get(i).getNumberOfArmies();
        }

        // deploy order logic
        Country l_currentAttacking = d_ownedCountries.get(0);
        for (int j = 0; j < d_player.getReinforcementCount(); j++) {
            for (Country l_traverse : d_ownedCountries) {
                if (l_traverse.getNumberOfArmies() < l_currentAttacking.getNumberOfArmies()) {
                    l_currentAttacking = l_traverse;
                }
            }
            l_currentAttacking.setNumberOfArmies(l_currentAttacking.getNumberOfArmies() + 1);
        }

        //set back for the orders
        for (int i = 0; i < d_ownedCountries.size(); i++) {
            d_countriesArmyValues[i] = d_ownedCountries.get(i).getNumberOfArmies();
            d_ownedCountries.get(i).setNumberOfArmies(d_temValue[i]);
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

        if (d_player.hasCard(CardType.BLOCKADE)) {
            d_ownedCountries = d_player.getAssignedCountries();
            int counting = 0;
            for (Country c : d_ownedCountries) {
                if (c.getNumberOfArmies() > counting) {
                    counting = c.getNumberOfArmies();
                    d_target = c;
                }
            }
            int count = 0;
            for (Country l_traverseCountry : d_target.getNeighbourCountries()) {
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
            int count2 = 0;
            count2 = d_player.getAssignedCountries().get(0).getNumberOfArmies();
            for (Country c : d_player.getAssignedCountries()) {
                if (c.getNumberOfArmies() > count) {
                    d_cardCountry = c;
                    count = c.getNumberOfArmies();
                }
                if (count2 > c.getNumberOfArmies()) {
                    d_target = c;
                    count2 = c.getNumberOfArmies();
                }
            }
            d_airlift = new AirliftOrder(d_cardCountry.getCountryName(), d_target.getCountryName(), String.valueOf(d_cardCountry.getNumberOfArmies() - 1), d_player);
            counter--;
        }
        if (counter != 0 && d_player.hasCard(CardType.DIPLOMACY)) {
            for (Country c : d_player.getAssignedCountries()) {
                for (Country c1 : c.getNeighbourCountries()) {
                    if (!c1.getOwnedBy().equals(d_player)) {
                        d_cardCountry = c1;
                    }
                }
            }
            d_negotiate = new NegotiateOrder(d_player, d_cardCountry.getOwnedBy().toString());
            counter--;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws InvalidArgumentException, EntityNotFoundException {
        deploy();
        d_logEntryBuffer.dataChanged("issue_order", String.format("%s player's turn to Issue Order", this.d_player.getName()));
        for (int i = 0; i < d_ownedCountries.size(); i++) {
            DeployOrder l_deployOrder = new DeployOrder(d_ownedCountries.get(i).getCountryName(), String.valueOf(d_countriesArmyValues[i] - d_ownedCountries.get(i).getNumberOfArmies()), d_player);
            this.d_player.addOrder(l_deployOrder);
        }
        cards();
        int counter = 1;
        if (d_player.hasCard(CardType.BLOCKADE)) {
            this.d_player.addOrder(d_blockade);
            counter--;
        }
        if (d_player.hasCard(CardType.AIRLIFT) && counter == 1) {
            this.d_player.addOrder(d_airlift);
            counter--;
        }
        if (d_player.hasCard(CardType.DIPLOMACY) && counter == 1) {
            this.d_player.addOrder(d_negotiate);
            counter--;
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
