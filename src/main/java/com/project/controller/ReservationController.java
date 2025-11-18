package com.project.controller;

import com.project.dao.CategoryPlaceDAO;
import com.project.dao.PaymentDetails;
import com.project.dao.ReservationDAO;
import com.project.exception.AnnulationTardiveException;
import com.project.service.Payable;
import com.project.service.PayableImpl;
import com.project.service.Reservable;
import com.project.service.ReservableImpl;

public class ReservationController {

    private final Reservable reservable = new ReservableImpl();
    private final Payable payable = new PayableImpl();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    public int getPlacesDisponibles(int categoryId){
        CategoryPlaceDAO categoryPlace = new CategoryPlaceDAO();
        return categoryPlace.getPlacesDisponibles(categoryId);
    }
    public boolean checkPlace(int clientId, int eventId, int categoryId, int quantity) throws Exception {
        return reservable.isReservable(clientId, eventId, categoryId, quantity);
    }

    private void reserve(int clientId, int eventId, int categoryId, int quantity) throws Exception {
        reservable.reserver(clientId, eventId, categoryId, quantity);
    }

    public void processPaymentAndReservation(
            int clientId,
            int eventId,
            int categoryId,
            int quantity,
            PaymentDetails details
    ) throws Exception {

        // 1. Vérifier paiement
        payable.payer(details);

        // 2. Réserver si paiement OK
        reserve(clientId, eventId, categoryId, quantity);
    }
    public void annulerReservation(int reservationId, int clientId)
            throws AnnulationTardiveException {

        reservationDAO.annulerReservation(reservationId, clientId);
    }
}