package com.warzone.team08.VM.exceptions;

/**
 * This Exception shows that the user input was invalid
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class InvalidCommandException extends VMException {
    public InvalidCommandException(String message) {
        super(message);
    }

    public InvalidCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}