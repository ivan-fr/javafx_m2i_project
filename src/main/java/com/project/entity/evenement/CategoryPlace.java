package com.project.entity.evenement;

import com.project.dao.PaymentDetails;
import com.project.exception.PaiementInvalideException;
import com.project.service.Payable;

/**
 * Represents a category of seats for an event.
 * Includes name, capacity, and price.
 * Payable - validates payment for this category.
 */
public class CategoryPlace implements Payable {
    private int id; // pour la BDD
    private int evenementId; // FK vers evenements
    private String categorie;
    private int nbPlaces;
    private double prix;

    /**
     * Constructor to create a category.
     * 
     * @param categorie Name of the category (e.g., "VIP").
     * @param nbPlaces  Number of available places.
     * @param prix      Price per ticket.
     */
    public CategoryPlace(String categorie, int nbPlaces, double prix) {
        this.categorie = categorie;
        this.nbPlaces = nbPlaces;
        this.prix = prix;
    }

    /**
     * Returns the category ID.
     * 
     * @return The ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the category ID.
     * 
     * @param id The new ID.
     */
    public void setId(int id) {
        this.id = id;
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
     * Returns the category name.
     * 
     * @return The name.
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Sets the category name.
     * 
     * @param categorie The new name.
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * Returns the number of places.
     * 
     * @return The capacity.
     */
    public int getNbPlaces() {
        return nbPlaces;
    }

    /**
     * Sets the number of places.
     * 
     * @param nbPlaces The new capacity.
     */
    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    /**
     * Returns the price.
     * 
     * @return The price per ticket.
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Sets the price.
     *
     * @param prix The new price.
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    /**
     * Validates payment for this category.
     * Checks payment details and ensures amount covers this category's price.
     *
     * @param details The payment details.
     * @return true if valid.
     * @throws PaiementInvalideException If validation fails.
     */
    @Override
    public boolean payer(PaymentDetails details) throws PaiementInvalideException {
        if (details.getNom() == null || details.getNom().isBlank()) {
            throw new PaiementInvalideException("Le nom du titulaire est obligatoire.");
        }

        if (details.getNumeroCarte() == null || !details.getNumeroCarte().matches("\\d{16}")) {
            throw new PaiementInvalideException("Numéro de carte invalide (doit contenir 16 chiffres).");
        }

        if (details.getMontant() < this.prix) {
            throw new PaiementInvalideException(
                    "Montant insuffisant pour " + this.categorie + ". Prix requis : " + this.prix + "€");
        }

        return true;
    }
}
