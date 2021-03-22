package com.warzone.team08.VM.constants.interfaces;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.exceptions.*;

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
     * @throws InvalidInputException    Throws if user input is invalid.
     * @throws EntityNotFoundException  Throws if the given country is not found in the list of available countries.
     * @throws InvalidCommandException  Throws if the command is invalid.
     * @throws InvalidArgumentException Throws if the argument value in the command is invalid.
     */
    void execute() throws EntityNotFoundException, InvalidInputException, InvalidCommandException, InvalidArgumentException, ResourceNotFoundException;

    /**
     * Gets the type of the order.
     *
     * @return Value of the order type.
     */
    OrderType getType();
}
