package com.project.dao;

public class PaymentDetails {
    private String nom;
    private String numeroCarte;

    public PaymentDetails(String nom, String numeroCarte) {
        this.nom = nom;
        this.numeroCarte = numeroCarte;
    }

    public String getNom() {
        return nom;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }
}