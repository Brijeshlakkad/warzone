package com.warzone.team08.VM.entities;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;

/**
 * This order represents the deploy order.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class DeployOrder implements Order {
    private final Player d_owner;
    private final Country d_targetCountry;
    private final int d_NumOfReinforcements;

    /**
     * Default parameterised constructor.
     *
     * @param p_owner               Player who has initiated this order.
     * @param p_targetCountry       Target country.
     * @param p_NumOfReinforcements Number of reinforcements to be deployed.
     */
    public DeployOrder(Player p_owner, Country p_targetCountry, int p_NumOfReinforcements) {
        d_owner = p_owner;
        d_targetCountry = p_targetCountry;
        d_NumOfReinforcements = p_NumOfReinforcements;
    }

    /**
     * Gets the type of order.
     *
     * @return Value of the order type.
     */
    public OrderType getType() {
        return OrderType.deploy;
    }

    /**
     * Gets the target country for the reinforcements.
     *
     * @return Value of the target country.
     */
    public Country getTargetCountry() {
        return d_targetCountry;
    }

    /**
     * Gets the number of reinforcements.
     *
     * @return Value of reinforcements number.
     */
    public int getNumOfReinforcements() {
        return d_NumOfReinforcements;
    }

    /**
     * Executes the order during <code>GameLoopState#EXECUTE_ORDER</code>
     */
    public void execute() {
        this.d_targetCountry.setNumberOfArmies(this.d_targetCountry.getNumberOfArmies() + this.d_NumOfReinforcements);
        this.d_owner.addExecutedOrder(this);
    }

    /**
     * Returns the string describing player order.
     *
     * @return String representing player orders.
     */
    @Override
    public String toString() {
        return String.format("Deploy order of %s player: %s %s", d_owner.getName(), d_targetCountry.getCountryName(), d_NumOfReinforcements);
    }
}
