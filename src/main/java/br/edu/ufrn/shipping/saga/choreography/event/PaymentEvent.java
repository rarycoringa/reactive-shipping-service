package br.edu.ufrn.shipping.saga.choreography.event;

public record PaymentEvent(
    EventType type
) implements Event{}
