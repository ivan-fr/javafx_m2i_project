package com.project.controller;

import com.project.dao.CategoryPlaceDAO;
import com.project.dao.PaymentDetails;
import com.project.dao.ReservationDAO;
import com.project.exception.AnnulationTardiveException;
import com.project.service.Payable;
import com.project.service.PayableImpl;
import com.project.service.Reservable;
import com.project.service.ReservableImpl;

/**
 * Controller for handling reservations and payments.
 */
public class ReservationController {

    private final Reservable reservable = new ReservableImpl();
    private final Payable payable = new PayableImpl();
    private final ReservationDAO reservationDAO = new ReservationDAO();

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
        return reservable.isReservable(clientId, eventId, categoryId, quantity);
    }

    /**
     * Internal method to perform the reservation.
     */
    private void reserve(int clientId, int eventId, int categoryId, int quantity) throws Exception {
        reservable.reserver(clientId, eventId, categoryId, quantity);
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

        // 1. Vérifier paiement
        payable.payer(details);

        // 2. Réserver si paiement OK
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