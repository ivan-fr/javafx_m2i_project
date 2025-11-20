package com.project.entity.reservation;

import com.project.exception.PlacesInsuffisantesException;

/**
 * Interface for validating reservations.
 * VALIDATION ONLY - does not handle persistence.
 * Entities implement this to validate business rules.
 */
public interface Reservable {

    /**
     * Validates if this reservation can be made.
     * Checks business rules: availability, valid data, etc.
     *
     * @param placesDisponibles Number of available places for this category.
     * @param quantite Number of places being requested.
     * @return true if this reservation passes validation.
     * @throws PlacesInsuffisantesException If validation fails.
     */
    boolean canReserve(int placesDisponibles, int quantite) throws PlacesInsuffisantesException;
}