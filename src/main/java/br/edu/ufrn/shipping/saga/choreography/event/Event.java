package br.edu.ufrn.shipping.saga.choreography.event;

public sealed interface Event permits PaymentEvent, ShippingEvent {
    EventType type();
    String orderId();
    String address();
}
