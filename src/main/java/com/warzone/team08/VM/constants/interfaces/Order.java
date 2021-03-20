package com.warzone.team08.VM.constants.interfaces;

import com.warzone.team08.VM.constants.enums.OrderType;

/**
 * This interface provides the methods to be implemented by different orders.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public interface Order {
    /**
     * Executes the order during <code>GameLoopState#EXECUTE_ORDER</code>
     */
    void execute();

    /**
     * Gets the type of the order.
     *
     * @return Value of the order type.
     */
    OrderType getType();
}
