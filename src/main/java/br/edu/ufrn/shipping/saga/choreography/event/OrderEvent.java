package br.edu.ufrn.shipping.saga.choreography.event;

public record OrderEvent(
    EventType type
) implements Event{}
