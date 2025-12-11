package br.edu.ufrn.shipping.saga.choreography.event;

public enum EventType {
    PAYMENT_CHARGED,
    PAYMENT_REFUSED,
    PAYMENT_REFUNDED,
    SHIPPING_ACCEPTED,
    SHIPPING_REFUSED
}
