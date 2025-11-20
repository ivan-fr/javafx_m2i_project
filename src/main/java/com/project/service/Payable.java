package com.project.service;

import com.project.dao.PaymentDetails;
import com.project.exception.PaiementInvalideException;

/**
 * Interface for payment processing.
 */
public interface Payable {
    /**
     * Process a payment.
     * 
     * @param details The payment details.
     * @return true if successful.
     * @throws PaiementInvalideException If payment details are invalid.
     */
    boolean payer(PaymentDetails details) throws PaiementInvalideException;
}