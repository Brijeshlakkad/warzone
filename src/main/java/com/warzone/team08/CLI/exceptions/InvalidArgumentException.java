package com.warzone.team08.CLI.exceptions;

/**
 * Exception to show user that provided argument and its value(s) are not valid
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}