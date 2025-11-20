package com.project.service;

import com.project.dao.CategoryPlaceDAO;
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
     * @param categoryDao The DAO to check availability (read-only).
     * @return true if this reservation passes validation.
     * @throws PlacesInsuffisantesException If validation fails.
     */
    boolean canReserve(CategoryPlaceDAO categoryDao) throws PlacesInsuffisantesException;
}