package br.edu.ufrn.shipping.saga.choreography.event;

public record ShippingEvent(
    EventType type
) implements Event{}
