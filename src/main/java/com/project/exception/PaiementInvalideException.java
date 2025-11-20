package com.project.exception;

/**
 * Exception thrown when a payment is invalid.
 */
public class PaiementInvalideException extends Exception {
    /**
     * Constructor with a message.
     * 
     * @param message The error message.
     */
    public PaiementInvalideException(String message) {
        super(message);
    }
}