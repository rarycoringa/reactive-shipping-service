package br.edu.ufrn.shipping.saga.choreography.event;

public record PaymentEvent(
    EventType type,
    String orderId,
    String address
) implements Event{}
