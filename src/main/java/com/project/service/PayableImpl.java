package com.project.service;

import com.project.dao.PaymentDetails;
import com.project.exception.PaiementInvalideException;

public class PayableImpl implements Payable {
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