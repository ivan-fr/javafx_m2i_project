package com.project.service;

import com.project.exception.PlacesInsuffisantesException;

/**
 * Interface for reservation services.
 */
public interface Reservable {

    /**
     * Checks if a reservation is possible.
     * 
     * @param userId     The user ID.
     * @param eventId    The event ID.
     * @param categoryId The category ID.
     * @param quantite   The number of places.
     * @return true if possible.
     * @throws PlacesInsuffisantesException If not enough places.
     */
    boolean isReservable(int userId, int eventId, int categoryId, int quantite)
            throws PlacesInsuffisantesException;

    /**
     * Makes a reservation.
     * 
     * @param userId     The user ID.
     * @param eventId    The event ID.
     * @param categoryId The category ID.
     * @param quantite   The number of places.
     * @throws PlacesInsuffisantesException If not enough places.
     */
    void reserver(int userId, int eventId, int categoryId, int quantite) throws PlacesInsuffisantesException;
}