package com.warzone.team08.VM.constants.interfaces;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.exceptions.InvalidOrderException;

/**
 * This interface provides the methods to be implemented by different orders.
 *
 * @author Brijesh Lakkad
 * @author CHARIT
 * @version 1.0
 */
public interface Order {

    /**
     * Executes the order during <code>GameLoopState#EXECUTE_ORDER</code>
     *
     * @throws InvalidOrderException If the order can not be performed due to an invalid country, an invalid number of
     *                               armies, or other invalid input.
     */
    void execute() throws InvalidOrderException;

    /**
     * Gets the type of the order.
     *
     * @return Value of the order type.
     */
    OrderType getType();
}
