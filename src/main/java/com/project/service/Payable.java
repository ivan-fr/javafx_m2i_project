package com.project.service;

import com.project.dao.PaymentDetails;
import com.project.exception.PaiementInvalideException;

public interface Payable {
    boolean payer(PaymentDetails details) throws PaiementInvalideException;
}