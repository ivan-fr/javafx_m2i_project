package com.project.exception;

/**
 * Exception thrown when there are not enough places available.
 */
public class PlacesInsuffisantesException extends Exception {
    /**
     * Constructor with a message.
     * 
     * @param message The error message.
     */
    public PlacesInsuffisantesException(String message) {
        super(message);
    }
}