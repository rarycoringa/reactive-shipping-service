package br.edu.ufrn.shipping.saga.choreography.event;

public sealed interface Event permits PaymentEvent, ShippingEvent {
    EventType type();
    String orderId();
    String productId();
    Integer productQuantity();
    String productName();
    Double productPrice();
    String chargeId();
    String refundId();
    Double amount();
    Integer splitInto();
    String cardNumber();
    String shippingId();
    String address();
}
