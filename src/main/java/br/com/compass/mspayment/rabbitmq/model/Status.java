package br.com.compass.mspayment.rabbitmq.model;

public enum Status {

    PROCESSING_PAYMENT,
    PAYMENT_SUCCESSFUL,
    PAYMENT_NOT_FOUND,
    PAYMENT_INACTIVE,
    PAYMENT_NOT_INSTALLMENT,
    PAYMENT_AMOUNT_NOT_AVAILABLE

}
