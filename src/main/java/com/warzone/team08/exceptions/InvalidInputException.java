package com.warzone.team08.exceptions;

/**
 * Exception to show user that provided argument and its value(s) are not valid
 *
 * @author CHARIT
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}