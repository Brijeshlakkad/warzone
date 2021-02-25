package com.warzone.team08.VM.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.responses.CommandResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    /**
     * List of orders issued by the player.
     */
    private final List<Order> d_orders;
    /**
     * List of orders executed by the <code>GameEngine</code>.
     */
    private final List<Order> d_executedOrders;
    private List<Country> d_assignedCountries;
    private int d_reinforcementsCount;
    private int d_remainingReinforcementCount;
    private boolean d_canReinforce;
    private int d_assignedCountryCount;
    /**
     * Keeps track of if the player has deployed the reinforcements of not.
     */
    private boolean d_hasDeployed;

    /**
     * Initializes variables required to handle player state.
     */
    public Player() {
        d_orders = new ArrayList<>();
        d_executedOrders = new ArrayList<>();
        d_assignedCountries = new ArrayList<>();
        d_reinforcementsCount = 0;
        d_remainingReinforcementCount = 0;
        d_canReinforce = true;
        d_assignedCountryCount = 0;
    }

    /**
     * Getter method for reinforcement armies.
     *
     * @return reinforcement armies.
     */
    public int getReinforcementCount() {
        return d_reinforcementsCount;
    }

    /**
     * Setter method to assign reinforce armies.
     *
     * @param p_reinforcementsCount reinforcement armies.
     */
    public void setReinforcementCount(int p_reinforcementsCount) {
        d_reinforcementsCount = p_reinforcementsCount;
        // Sets the count for remaining number of reinforcements as well.
        d_remainingReinforcementCount = p_reinforcementsCount;
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
     * Adds the order to the list of player's orders.
     *
     * @param p_order Order to be added.
     */
    public void addOrder(Order p_order) {
        d_orders.add(p_order);
    }

    /**
     * Gets the number of remaining reinforcements.
     *
     * @return Total number of remaining reinforcements.
     */
    public int getRemainingReinforcementCount() {
        return d_remainingReinforcementCount;
    }

    /**
     * Sets the remaining reinforcement army
     *
     * @param p_remainingReinforcementCount reinforcement count
     */
    public void setRemainingReinforcementCount(int p_remainingReinforcementCount) {
        d_remainingReinforcementCount = p_remainingReinforcementCount;
    }

    /**
     * Reduces the reinforcements. Checks if the player has already deployed the reinforcements.
     *
     * @return True if the player has deployed the reinforcements.
     */
    public boolean isHasDeployed() {
        return d_hasDeployed;
    }

    /**
     * Sets the value indicating that if the player has deployed the reinforcements.
     *
     * @param p_hasDeployed Value of true if the player has deployed the reinforcements.
     */
    public void setHasDeployed(boolean p_hasDeployed) {
        d_hasDeployed = p_hasDeployed;
    }

    /**
     * Calculates the number of remaining reinforcements for the player.
     * <p>Shows error if the player orders more than
     * reinforcements than in possession. This method also sets the remaining number of reinforcements left for the
     * future use.
     *
     * @param p_usedReinforcementCount Total number of used reinforcements.
     * @return True if the player can reinforce the armies; false otherwise.
     */
    public boolean canPlayerReinforce(int p_usedReinforcementCount) {
        if (this.getRemainingReinforcementCount() == 0) {
            return false;
        }
        int l_remainingReinforcementCount = this.getRemainingReinforcementCount() - p_usedReinforcementCount;
        if (l_remainingReinforcementCount < 0) {
            return false;
        }
        this.setRemainingReinforcementCount(l_remainingReinforcementCount);
        return true;
    }

    /**
     * Gets order from the user and stores the order for the player.
     *
     * @throws ReinforcementOutOfBoundException If the player doesn't have enough reinforcement to issue the order.
     * @throws InvalidCommandException          If there is an error while preprocessing the user command.
     * @throws InvalidArgumentException         If the mentioned value is not of expected type.
     * @throws EntityNotFoundException          If the target country not found.
     * @throws ExecutionException               If any error while processing concurrent thread.
     * @throws InterruptedException             If scheduled thread was interrupted.
     */
    public void issueOrder() throws
            ReinforcementOutOfBoundException, InvalidCommandException, EntityNotFoundException, ExecutionException, InterruptedException, InvalidArgumentException {
        // Requests user interface for input from user.
        String l_responseVal = "";
        do {
            VirtualMachine.getInstance().stdout(String.format("\nPlayer: %s--------\nUSAGE: You can check map details\n> showmap <return>", this.d_name, this.d_remainingReinforcementCount));
            Future<String> l_responseOfFuture = VirtualMachine.getInstance().askForUserInput(String.format("Issue Order:"));
            l_responseVal = l_responseOfFuture.get();
        } while (l_responseVal.isEmpty());
        try {
            ObjectMapper l_objectMapper = new ObjectMapper();
            CommandResponse l_commandResponse = l_objectMapper.readValue(l_responseVal, CommandResponse.class);
            Order l_newOrder = Order.map(l_commandResponse);
            if (l_newOrder.getOrderType() == OrderType.deploy) {
                if (this.getAssignedCountries().contains(l_newOrder.getCountry())) {
                    if (this.getRemainingReinforcementCount() != 0 && this.canPlayerReinforce(l_newOrder.getNumOfReinforcements())) {
                        l_newOrder.setOwner(this);
                        this.addOrder(l_newOrder);
                    } else {
                        throw new ReinforcementOutOfBoundException("You don't have enough reinforcements.");
                    }
                } else {
                    throw new InvalidCommandException("You can deploy the reinforcements only in your assigned countries");
                }
            }
        } catch (IOException p_ioException) {
            throw new InvalidCommandException("Unrecognised input!");
        }
    }

    /**
     * Adds the order to the list of player's executed orders.
     *
     * @param p_executedOrder Executed order to be added.
     */
    public void addExecutedOrder(Order p_executedOrder) {
        d_executedOrders.add(p_executedOrder);
    }

    /**
     * Gets the list of executed orders.
     *
     * @return Value of the list of orders.
     */
    public List<Order> getExecutedOrders() {
        return d_executedOrders;
    }

    /**
     * Checks if the player has any order for execution.
     *
     * @return Value of true if the player has one or more orders.
     */
    public boolean hasOrders() {
        return d_orders.size() > 0;
    }

    /**
     * Gets the first order from the order list and removes the order from the list before returning it.
     *
     * @return Value of order to be executed.
     * @throws OrderOutOfBoundException If the player does not have any order.
     */
    public Order nextOrder() throws OrderOutOfBoundException {
        if (this.hasOrders())
            return d_orders.remove(0);
        else {
            throw new OrderOutOfBoundException("No order left for execution.");
        }
    }
}
