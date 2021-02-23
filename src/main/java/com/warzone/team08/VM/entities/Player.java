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
    private String d_name;
    private List<Order> d_orders;
    private List<Country> d_assignedCountries;
    private int d_reinforcementArmies;
    private int d_assignedCountryCount;

    public Player() {
        d_orders = new ArrayList<>();
        d_assignedCountries = new ArrayList<>();
    }

    /**
     * Getter method for assigned country count.
     *
     * @return assigned country count.
     */
    public int getAssignedCountryCount() {
        return d_assignedCountryCount;
    }

    /**
     * Setter method for assigned country count.
     *
     * @param p_assignedCountryCount Assigned country count.
     */
    public void setAssignedCountryCount(int p_assignedCountryCount) {
        d_assignedCountryCount = p_assignedCountryCount;
    }

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
