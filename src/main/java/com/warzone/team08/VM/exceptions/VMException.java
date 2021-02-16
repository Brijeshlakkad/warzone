package com.warzone.team08.VM.exceptions;

/**
 * Base Exception for exceptions occurred in Virtual Machine
 *
 * @author CHARIT
 * @author Brijesh Lakkad
 */
public class VMException extends Exception {
    /**
     * Parameterized constructor to call parent class constructor.
     *
     * @param p_message Error message string
     */
    public VMException(String p_message) {
        super(p_message);
    }

    public VMException(String p_message, Throwable p_cause) {
        super(p_message, p_cause);
    }
}
