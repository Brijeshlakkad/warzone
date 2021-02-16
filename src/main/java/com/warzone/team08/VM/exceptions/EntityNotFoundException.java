package com.warzone.team08.VM.exceptions;

/**
 * Exception to show that entity not found while searching in repository.
 *
 * @author Brijesh Lakkad
 */
public class EntityNotFoundException extends VMException {
    /**
     * Parameterized constructor to call parent class constructor.
     *
     * @param p_message Error message string
     */
    public EntityNotFoundException(String p_message) {
        super(p_message);
    }

    public EntityNotFoundException(String p_message, Throwable p_cause) {
        super(p_message, p_cause);
    }
}
