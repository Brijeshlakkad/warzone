package com.warzone.team08.VM.exceptions;

/**
 * Exception to show that resource was not found.ø
 *
 * @author CHARIT
 * @author Brijesh Lakkad
 */
public class ResourceNotFoundException extends VMException {
    /**
     * Parameterized constructor to call parent class constructor.
     *
     * @param p_message Error message string
     */
    public ResourceNotFoundException(String p_message) {
        super(p_message);
    }

    public ResourceNotFoundException(String p_message, Throwable p_cause) {
        super(p_message, p_cause);
    }
}