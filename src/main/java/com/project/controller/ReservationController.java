package com.project.controller;

import com.project.dao.CategoryPlaceDAO;
import com.project.dao.PaymentDetails;
import com.project.dao.ReservationDAO;
import com.project.entity.evenement.CategoryPlace;
import com.project.entity.evenement.Payable;
import com.project.entity.reservation.Reservable;
import com.project.entity.reservation.Reservation;
import com.project.exception.AnnulationTardiveException;

import java.time.LocalDateTime;

/**
 * Controller for handling reservations and payments.
 * Uses polymorphism with Payable and Reservable interfaces for better architecture.
 */
public class ReservationController {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final CategoryPlaceDAO categoryPlaceDAO = new CategoryPlaceDAO();

    /**
     * Gets a category by its ID as a Payable object.
     * Uses polymorphism to return the interface type.
     *
     * @param categoryId The category ID.
     * @return The Payable object (CategoryPlace implementation).
     */
    public Payable getPayableCategoryById(int categoryId) {
        return categoryPlaceDAO.getCategoryById(categoryId);
    }

    /**
     * Gets a category by its ID as a concrete CategoryPlace.
     * Use this when you need access to specific CategoryPlace methods.
     *
     * @param categoryId The category ID.
     * @return The CategoryPlace object.
     */
    public CategoryPlace getCategoryPlaceById(int categoryId) {
        return categoryPlaceDAO.getCategoryById(categoryId);
    }

    /**
     * Gets the number of available places for a category.
     *
     * @param categoryId The category ID.
     * @return Number of available places.
     */
    public int getPlacesDisponibles(int categoryId) {
        return categoryPlaceDAO.getPlacesDisponibles(categoryId);
    }

    /**
     * Validates if a payment is possible for a given category.
     * Uses Payable interface for polymorphic payment validation.
     *
     * @param payable The payable entity (e.g., CategoryPlace).
     * @param details The payment details to validate.
     * @return true if payment is valid.
     * @throws Exception If payment validation fails.
     */
    public boolean validatePayment(Payable payable, PaymentDetails details) throws Exception {
        if (payable == null) {
            throw new Exception("Entité payable invalide");
        }
        return payable.canPay(details);
    }

    /**
     * Validates if a reservation is possible.
     * Uses Reservable interface for polymorphic reservation validation.
     *
     * @param reservable        The reservable entity (e.g., Reservation).
     * @param placesDisponibles Number of available places.
     * @param quantity          Number of places requested.
     * @return true if reservation is valid.
     * @throws Exception If validation fails.
     */
    public boolean validateReservation(Reservable reservable, int placesDisponibles, int quantity) throws Exception {
        if (reservable == null) {
            throw new Exception("Entité reservable invalide");
        }
        return reservable.canReserve(placesDisponibles, quantity);
    }

    /**
     * Checks if a reservation is possible.
     * Creates a temporary Reservable to perform validation using polymorphism.
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

        // Create temporary Reservable for validation
        Reservable tempReservation = new Reservation(clientId, eventId, categoryId, LocalDateTime.now());

        // Use polymorphic validation
        return validateReservation(tempReservation, placesDisponibles, quantity);
    }

    /**
     * Internal method to perform the reservation.
     * Creates individual Reservation instances for each place.
     * Validates each reservation using Reservable interface before saving.
     */
    private void reserve(int clientId, int eventId, int categoryId, int quantity) throws Exception {
        int placesDisponibles = categoryPlaceDAO.getPlacesDisponibles(categoryId);

        for (int i = 0; i < quantity; i++) {
            // Create concrete instance
            Reservation reservation = new Reservation(clientId, eventId, categoryId, LocalDateTime.now());

            // Validate using polymorphism (Reservable interface)
            if (validateReservation(reservation, placesDisponibles, quantity)) {
                // Save (persistence - handled by controller/DAO)
                reservationDAO.ajouterReservation(reservation);
            }
        }
    }

    /**
     * Processes payment and creates a reservation.
     * Uses polymorphic Payable interface for payment validation.
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

        // 1. Fetch category as Payable (polymorphism)
        Payable payableCategory = getPayableCategoryById(categoryId);

        // 2. Validate payment using polymorphic interface
        validatePayment(payableCategory, details);

        // 3. Reserve if payment OK
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