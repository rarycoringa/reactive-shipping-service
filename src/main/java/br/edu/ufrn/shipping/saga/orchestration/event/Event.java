package br.edu.ufrn.shipping.saga.orchestration.event;

public record Event(
    EventType type,
    String orderId,
    String shippingId,
    String address
) {}
