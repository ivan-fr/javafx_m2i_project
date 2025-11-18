package com.project.service;

import com.project.exception.PlacesInsuffisantesException;

public interface Reservable {

    boolean isReservable(int userId, int eventId, int categoryId, int quantite)
            throws PlacesInsuffisantesException;

    void reserver(int userId, int  eventId, int categoryId, int quantite) throws PlacesInsuffisantesException;
}