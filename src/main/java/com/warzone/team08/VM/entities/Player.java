package com.warzone.team08.VM.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class provides different getter-setter methods to perform different operation on Continent entity.
 *
 * @author CHARIT
 * @author Brijesh Lakkad
 */
public class Player {
    private List<Order> d_orders = new ArrayList<>();
    private int d_turnValue;
    private ArrayList<Country> d_assignedCountries;
    private String d_name;
    private int d_initialArmies;
    private int d_reinforcementArmies;
    private boolean d_canReinforce;

    /**
     * Getter method for reinforcement armies.
     *
     * @return reinforcement armies.
     */
    public int getReinforcementArmies() {
        return d_reinforcementArmies;
    }

    /**
     * Setter method to assign reinforce armies.
     *
     * @param p_reinforcementArmies reinforcement armies.
     */
    public void setReinforcementArmies(int p_reinforcementArmies) {
        d_reinforcementArmies = p_reinforcementArmies;
    }

    /**
     * getter method to check can reinforce.
     *
     * @return true, if player can reinforce armies, otherwise false.
     */
    public boolean isCanReinforce() {
        return d_canReinforce;
    }

    /**
     * Setter method to assign reinforce value.
     *
     * @param p_canReinforce true/false value according to player can reinforcement or not.
     */
    public void setCanReinforce(boolean p_canReinforce) {
        d_canReinforce = p_canReinforce;
    }

    /**
     * Getter method for initial armies.
     * @return initial armies.
     */
    public int getInitialArmies() {
        return d_initialArmies;
    }

    /**
     * Setter method for initial armies.
     * @param p_initialArmies initial armies.
     */
    public void setInitialArmies(int p_initialArmies) {
        d_initialArmies = p_initialArmies;
    }

    /**
     * Getter method for player name.
     * @return player name.
     */
    public String getName() {
        return d_name;
    }

    /**
     * Setter method for player name.
     * @param p_name player name.
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * getter method to assign countries.
     *
     * @return list of assigned countries.
     */
    public ArrayList<Country> getAssignedCountries() {
        return d_assignedCountries;
    }

    /**
     * setter method to assign countries.
     *
     * @param p_assignedCountries list of assigned countries.
     */
    public void setAssignedCountries(ArrayList<Country> p_assignedCountries) {
        d_assignedCountries = p_assignedCountries;
    }

    /**
     * Gets the turn value of the particular player.
     *
     * @return turn value.
     */
    public int getTurnValue() {
        return d_turnValue;
    }

    /**
     * Sets the turn value of the particular player.
     *
     * @param p_turnValue turn value.
     */
    public void setTurnValue(int p_turnValue) {
        d_turnValue = p_turnValue;
    }

    /**
     * Gets order from the user and stores the order for the player.
     */
    void issueOrder() {
        // TODO Brijesh Lakkad Get an order from user
    }

    /**
     * Gets the first order from the order list and removes the order from the list before returning it.
     *
     * @return Value of order to be executed.
     */
    Order nextOrder() {
        // TODO Deep Patel get the order for execution
        return new Order();
    }
}
