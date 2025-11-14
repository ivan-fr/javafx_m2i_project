package com.project.entity;

import java.time.LocalDateTime;

public class Reservation {
    private int id; // id en BDD
    private int clientId; // FK vers utilisateurs (CLIENT)
    private int evenementId; // FK vers evenements
    private int categoryPlaceId; // FK vers category_places
    private LocalDateTime dateReservation;

    public Reservation() {
    }

    public Reservation(int clientId, int evenementId, int categoryPlaceId, LocalDateTime dateReservation) {
        this.clientId = clientId;
        this.evenementId = evenementId;
        this.categoryPlaceId = categoryPlaceId;
        this.dateReservation = dateReservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(int evenementId) {
        this.evenementId = evenementId;
    }

    public int getCategoryPlaceId() {
        return categoryPlaceId;
    }

    public void setCategoryPlaceId(int categoryPlaceId) {
        this.categoryPlaceId = categoryPlaceId;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }
}