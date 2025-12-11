package br.edu.ufrn.shipping.saga.processor.event;

public record Event(
    EventType type,
    String orderId,
    String shippingId
) {}
