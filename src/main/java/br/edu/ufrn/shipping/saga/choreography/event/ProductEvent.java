package br.edu.ufrn.shipping.saga.choreography.event;

public record ProductEvent(
    EventType type
) implements Event{}
