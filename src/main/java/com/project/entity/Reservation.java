package com.project.entity;

import com.project.exception.PlacesInsuffisantesException;
import com.project.service.Reservable;

import java.time.LocalDateTime;

/**
 * Represents a reservation made by a client.
 * Links a user, an event, and a seat category.
 * Implements Reservable to handle reservation logic.
 */
public class Reservation implements Reservable {
    private int id; // id en BDD
    private int clientId; // FK vers utilisateurs (CLIENT)
    private int evenementId; // FK vers evenements
    private int categoryPlaceId; // FK vers category_places
    private LocalDateTime dateReservation;

    private String evenementNom;
    private LocalDateTime evenementDate;
    private String categorie;
    private int nombrePlaces;
    private double prixUnitaire;
    private double prixTotal;

    /**
     * Default constructor.
     */
    public Reservation() {
    }

    /**
     * Constructor to create a new reservation.
     *
     * @param clientId        The client's ID.
     * @param evenementId     The event's ID.
     * @param categoryPlaceId The category ID.
     * @param dateReservation The date of reservation.
     */
    public Reservation(int clientId, int evenementId, int categoryPlaceId, LocalDateTime dateReservation) {
        this.clientId = clientId;
        this.evenementId = evenementId;
        this.categoryPlaceId = categoryPlaceId;
        this.dateReservation = dateReservation;
    }

    /**
     * Returns the reservation ID.
     * 
     * @return The ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the reservation ID.
     * 
     * @param id The new ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the client ID.
     * 
     * @return The client ID.
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets the client ID.
     * 
     * @param clientId The new client ID.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Returns the event ID.
     * 
     * @return The event ID.
     */
    public int getEvenementId() {
        return evenementId;
    }

    /**
     * Sets the event ID.
     * 
     * @param evenementId The new event ID.
     */
    public void setEvenementId(int evenementId) {
        this.evenementId = evenementId;
    }

    /**
     * Returns the category ID.
     * 
     * @return The category ID.
     */
    public int getCategoryPlaceId() {
        return categoryPlaceId;
    }

    /**
     * Sets the category ID.
     * 
     * @param categoryPlaceId The new category ID.
     */
    public void setCategoryPlaceId(int categoryPlaceId) {
        this.categoryPlaceId = categoryPlaceId;
    }

    /**
     * Returns the reservation date.
     * 
     * @return The date and time.
     */
    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    /**
     * Sets the reservation date.
     * 
     * @param dateReservation The new date and time.
     */
    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    /**
     * Returns the event name.
     * 
     * @return The event name.
     */
    public String getEvenementNom() {
        return evenementNom;
    }

    /**
     * Sets the event name.
     * 
     * @param evenementNom The new event name.
     */
    public void setEvenementNom(String evenementNom) {
        this.evenementNom = evenementNom;
    }

    /**
     * Returns the event date.
     * 
     * @return The event date.
     */
    public LocalDateTime getEvenementDate() {
        return evenementDate;
    }

    /**
     * Sets the event date.
     * 
     * @param evenementDate The new event date.
     */
    public void setEvenementDate(LocalDateTime evenementDate) {
        this.evenementDate = evenementDate;
    }

    /**
     * Returns the category name.
     * 
     * @return The category name.
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Sets the category name.
     * 
     * @param categorie The new category name.
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * Returns the number of places.
     * 
     * @return The number of places.
     */
    public int getNombrePlaces() {
        return nombrePlaces;
    }

    /**
     * Sets the number of places.
     * 
     * @param nombrePlaces The new number of places.
     */
    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    /**
     * Returns the unit price.
     * 
     * @return The price per ticket.
     */
    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    /**
     * Sets the unit price.
     * 
     * @param prixUnitaire The new unit price.
     */
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    /**
     * Returns the total price.
     * 
     * @return The total price.
     */
    public double getPrixTotal() {
        return prixTotal;
    }

    /**
     * Sets the total price.
     *
     * @param prixTotal The new total price.
     */
    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    /**
     * Validates if this reservation can be made.
     * Checks business rules: availability, valid data, etc.
     * VALIDATION ONLY - does not save to database.
     *
     * @param placesDisponibles Number of available places for this category.
     * @param quantite Number of places being requested.
     * @return true if this reservation passes validation.
     * @throws PlacesInsuffisantesException If validation fails.
     */
    @Override
    public boolean canReserve(int placesDisponibles, int quantite) throws PlacesInsuffisantesException {
        // Validate required fields
        if (this.categoryPlaceId <= 0 || this.clientId <= 0 || this.evenementId <= 0) {
            throw new PlacesInsuffisantesException("Données de réservation invalides");
        }

        // Check availability
        if (placesDisponibles < quantite) {
            System.out.println("Erreur : Plus de places disponibles pour cette catégorie");
            throw new PlacesInsuffisantesException("Pas assez de places !");
        }

        return true;
    }
}
