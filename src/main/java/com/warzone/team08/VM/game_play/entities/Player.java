package com.warzone.team08.VM.game_play.entities;

import com.warzone.team08.VM.map_editor.entities.Country;

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
    private Set<Country> d_countrySet;
    private List<Order> d_orders = new ArrayList<>();

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
