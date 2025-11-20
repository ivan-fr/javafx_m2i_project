package com.project.entity.evenement;

import com.project.dao.PaymentDetails;
import com.project.exception.PaiementInvalideException;

/**
 * Interface for payment validation.
 */
public interface Payable {
    /**
     * Validates payment details.
     *
     * @param details The payment details.
     * @return true if payment is valid.
     * @throws PaiementInvalideException If payment details are invalid.
     */
    boolean canPay(PaymentDetails details) throws PaiementInvalideException;
}