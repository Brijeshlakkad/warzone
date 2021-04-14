package com.warzone.team08.VM.constants.interfaces;

import com.warzone.team08.VM.VirtualMachine;
import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.exceptions.CardNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidOrderException;
import com.warzone.team08.VM.game_play.GamePlayEngine;

/**
 * This interface provides the methods to be implemented by different orders.
 *
 * @author Brijesh Lakkad
 * @author CHARIT
 * @version 1.0
 */
public abstract class Order {
    /**
     * Execution game-play-index to define when this order supposed to be executed.
     */
    private final int d_executionIndex;
    /**
     * Defines when this order supposed to be expired.
     */
    private int d_expiryIndex = -1;

    /**
     * Default constructor.
     */
    public Order() {
        if (this.getType() == OrderType.negotiate) {
            d_executionIndex = GamePlayEngine.getCurrentExecutionIndex() + 1;
            d_expiryIndex = d_executionIndex + 1;
            VirtualMachine.getGameEngine().getGamePlayEngine().addFutureOrder(this);
        } else {
            d_executionIndex = GamePlayEngine.getCurrentExecutionIndex();
        }
    }

    /**
     * Executes the order during <code>GameLoopState#EXECUTE_ORDER</code>
     *
     * @throws InvalidOrderException If the order can not be performed due to an invalid country, an invalid number of
     *                               armies, or other invalid input.
     * @throws CardNotFoundException Card doesn't found in the player's card list.
     */
    public abstract void execute() throws InvalidOrderException, CardNotFoundException;

    /**
     * Gets the type of the order.
     *
     * @return Value of the order type.
     */
    public abstract OrderType getType();

    /**
     * Gets the execution index of this order.
     *
     * @return Value of the execution index.
     */
    public int getExecutionIndex() {
        return d_executionIndex;
    }

    /**
     * Gets the expiration index for this order.
     *
     * @return Value of the expiration index.
     */
    public int getExpiryIndex() {
        return d_expiryIndex;
    }

    /**
     * Reverse the effect of the order or makes the card expired which was previously been executed.
     */
    abstract public void expire();
}
