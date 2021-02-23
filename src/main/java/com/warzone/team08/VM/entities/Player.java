package com.warzone.team08.VM.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides different getter-setter methods to perform different operation on Continent entity.
 *
 * @author CHARIT
 * @author Brijesh Lakkad
 */
public class Player {
    /**
     * Represents the unique name of each player.
     */
    private String d_name;
    private final List<Order> d_orders;
    private List<Country> d_assignedCountries;
    private int d_reinforcementCount;
    private int d_assignedCountryCount;

    public Player() {
        d_orders = new ArrayList<>();
        d_assignedCountries = new ArrayList<>();
        d_reinforcementCount = 0;
        d_assignedCountryCount = 0;
    }

    /**
     * Getter method to get the count of reinforcement armies.
     *
     * @return reinforcement armies.
     */
    public int getReinforcementCount() {
        return d_reinforcementCount;
    }

    /**
     * Setter method to assign reinforce armies count.
     *
     * @param p_reinforcementArmies reinforcement armies.
     */
    public void setReinforcementArmies(int p_reinforcementArmies) {
        d_reinforcementCount = p_reinforcementArmies;
    }

    /**
     * Getter method for player name.
     *
     * @return player name.
     */
    public String getName() {
        return d_name;
    }

    /**
     * Setter method for player name.
     *
     * @param p_name player name.
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Setter method to assign countries.
     *
     * @return list of assigned countries.
     */
    public List<Country> getAssignedCountries() {
        return d_assignedCountries;
    }

    /**
     * Setter method to assign countries.
     *
     * @param p_assignedCountries list of assigned countries.
     */
    public void setAssignedCountries(List<Country> p_assignedCountries) {
        d_assignedCountries = p_assignedCountries;
    }

    /**
     * Adds assigned country to the list.
     *
     * @param p_assignedCountry Value of assigned countries.
     */
    public void addAssignedCountries(Country p_assignedCountry) {
        d_assignedCountries.add(p_assignedCountry);
    }

    /**
     * Gets the number of assigned countries for this player.
     *
     * @return Value of the count.
     */
    public int getAssignedCountryCount() {
        return d_assignedCountryCount;
    }

    /**
     * Sets the number of countries that will be assigned to this player.
     *
     * @param p_assignedCountryCount Value of the count to be set.
     */
    public void setAssignedCountryCount(int p_assignedCountryCount) {
        d_assignedCountryCount = p_assignedCountryCount;
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
