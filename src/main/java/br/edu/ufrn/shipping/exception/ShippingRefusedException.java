package br.edu.ufrn.shipping.exception;

public class ShippingRefusedException extends RuntimeException {
    private static final String message = "Shipping refused.";

    public ShippingRefusedException() {
        super(message);
    }
}
