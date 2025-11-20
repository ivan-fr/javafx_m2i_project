package com.project.exception;

/**
 * Exception thrown when a cancellation is attempted too late.
 */
public class AnnulationTardiveException extends Exception {
    /**
     * Constructor with a message.
     * 
     * @param message The error message.
     */
    public AnnulationTardiveException(String message) {
        super(message);
    }
}