package com.project.controller;

import com.project.dao.CategoryPlaceDAO;
import com.project.dao.PaymentDetails;
import com.project.dao.ReservationDAO;
import com.project.entity.Reservation;
import com.project.exception.AnnulationTardiveException;

import java.time.LocalDateTime;

/**
 * Controller for handling reservations and payments.
 */
public class ReservationController {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final CategoryPlaceDAO categoryPlaceDAO = new CategoryPlaceDAO();

    /**
     * Gets a category by its ID.
     *
     * @param categoryId The category ID.
     * @return The CategoryPlace object.
     */
    public com.project.entity.evenement.CategoryPlace getCategoryPlaceById(int categoryId) {
        return categoryPlaceDAO.getCategoryById(categoryId);
    }

    /**
     * Gets the number of available places for a category.
     *
     * @param categoryId The category ID.
     * @return Number of available places.
     */
    public int getPlacesDisponibles(int categoryId) {
        CategoryPlaceDAO categoryPlace = new CategoryPlaceDAO();
        return categoryPlace.getPlacesDisponibles(categoryId);
    }

    /**
     * Checks if a reservation is possible.
     *
     * @param clientId   The client ID.
     * @param eventId    The event ID.
     * @param categoryId The category ID.
     * @param quantity   Number of places.
     * @return true if possible.
     * @throws Exception If an error occurs.
     */
    public boolean checkPlace(int clientId, int eventId, int categoryId, int quantity) throws Exception {
        int placesDisponibles = categoryPlaceDAO.getPlacesDisponibles(categoryId);
        return placesDisponibles >= quantity;
    }

    /**
     * Internal method to perform the reservation.
     * Creates individual Reservation instances for each place.
     * Validates each reservation before saving.
     */
    private void reserve(int clientId, int eventId, int categoryId, int quantity) throws Exception {
        int placesDisponibles = categoryPlaceDAO.getPlacesDisponibles(categoryId);

        for (int i = 0; i < quantity; i++) {
            Reservation reservation = new Reservation(clientId, eventId, categoryId, LocalDateTime.now());

            // Validate (business rules)
            if (reservation.canReserve(placesDisponibles, quantity)) {
                // Save (persistence - handled by controller/DAO)
                reservationDAO.ajouterReservation(reservation);
            }
        }
    }

    /**
     * Processes payment and creates a reservation.
     *
     * @param clientId   The client ID.
     * @param eventId    The event ID.
     * @param categoryId The category ID.
     * @param quantity   Number of places.
     * @param details    Payment details.
     * @throws Exception If payment or reservation fails.
     */
    public void processPaymentAndReservation(
            int clientId,
            int eventId,
            int categoryId,
            int quantity,
            PaymentDetails details) throws Exception {

        // 1. Fetch category and verify payment
        var category = categoryPlaceDAO.getCategoryById(categoryId);
        if (category == null) {
            throw new Exception("Catégorie invalide");
        }
        category.canPay(details);

        // 2. Reserve if payment OK
        reserve(clientId, eventId, categoryId, quantity);
    }

    /**
     * Cancels a reservation.
     * 
     * @param reservationId The reservation ID.
     * @param clientId      The client ID.
     * @throws AnnulationTardiveException If cancelled too late.
     */
    public void annulerReservation(int reservationId, int clientId)
            throws AnnulationTardiveException {

        reservationDAO.annulerReservation(reservationId, clientId);
    }
}