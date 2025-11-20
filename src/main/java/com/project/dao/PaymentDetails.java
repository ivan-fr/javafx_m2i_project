package com.project.dao;

/**
 * Helper class to hold payment information.
 */
public class PaymentDetails {
    private String nom;
    private String numeroCarte;

    /**
     * Constructor.
     * 
     * @param nom         Name on the card.
     * @param numeroCarte Card number.
     */
    public PaymentDetails(String nom, String numeroCarte) {
        this.nom = nom;
        this.numeroCarte = numeroCarte;
    }

    /**
     * Returns the name.
     * 
     * @return The name on the card.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Returns the card number.
     * 
     * @return The card number.
     */
    public String getNumeroCarte() {
        return numeroCarte;
    }
}