package com.project.dao;

/**
 * Helper class to hold payment information.
 */
public class PaymentDetails {
    private String nom;
    private String numeroCarte;
    private double montant;

    /**
     * Constructor.
     *
     * @param nom         Name on the card.
     * @param numeroCarte Card number.
     * @param montant     Payment amount.
     */
    public PaymentDetails(String nom, String numeroCarte, double montant) {
        this.nom = nom;
        this.numeroCarte = numeroCarte;
        this.montant = montant;
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

    /**
     * Returns the payment amount.
     *
     * @return The amount.
     */
    public double getMontant() {
        return montant;
    }
}