package com.project.service;

import com.project.dao.CategoryPlaceDAO;
import com.project.dao.ReservationDAO;
import com.project.entity.Reservation;
import com.project.exception.PlacesInsuffisantesException;

import java.time.LocalDateTime;

/**
 * Implementation of the reservation service.
 * Handles availability checks and booking.
 */
public class ReservableImpl implements Reservable {

    private final CategoryPlaceDAO categoryDao;
    private final ReservationDAO reservationDao;

    /**
     * Constructor.
     * Initializes DAOs.
     */
    public ReservableImpl() {
        categoryDao = new CategoryPlaceDAO();
        reservationDao = new ReservationDAO();
    }

    /**
     * Checks if enough places are available.
     * 
     * @param userId     The user ID.
     * @param eventId    The event ID.
     * @param categoryId The category ID.
     * @param quantite   The number of places.
     * @return true if available.
     * @throws PlacesInsuffisantesException If not enough places.
     */
    @Override
    public boolean isReservable(int userId, int eventId, int categoryId, int quantite)
            throws PlacesInsuffisantesException {

        int placesDisponibles = categoryDao.getPlacesDisponibles(categoryId);
        if (placesDisponibles < quantite) {
            System.out.println("Erreur : Plus de places disponibles pour cette catégorie");
            throw new PlacesInsuffisantesException("Pas assez de places !");
        }
        return true;

    }

    /**
     * Creates reservations for the specified quantity.
     * 
     * @param userId     The user ID.
     * @param eventId    The event ID.
     * @param categoryId The category ID.
     * @param quantite   The number of places.
     * @throws PlacesInsuffisantesException If not enough places.
     */
    @Override
    public void reserver(int userId, int eventId, int categoryId, int quantite)
            throws PlacesInsuffisantesException {

        boolean isReservable = isReservable(userId, eventId, categoryId, quantite);
        if (isReservable) {
            for (int i = 0; i < quantite; i++) {
                Reservation reservation = new Reservation(userId, eventId, categoryId, LocalDateTime.now());
                reservationDao.ajouterReservation(reservation);
            }
        }

    }
}