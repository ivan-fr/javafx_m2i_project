package com.project.service;

import com.project.dao.PaymentDetails;
import com.project.exception.PaiementInvalideException;

/**
 * Implementation of the payment service.
 * Validates payment details.
 */
public class PayableImpl implements Payable {
    /**
     * Validates and processes the payment.
     * Checks for valid name and card number format.
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

        return true; // OK si valide
    }
}