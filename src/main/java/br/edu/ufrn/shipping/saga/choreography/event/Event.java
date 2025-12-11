package br.edu.ufrn.shipping.saga.choreography.event;

public sealed interface Event permits OrderEvent, ProductEvent, PaymentEvent, ShippingEvent {
    EventType type();
}
