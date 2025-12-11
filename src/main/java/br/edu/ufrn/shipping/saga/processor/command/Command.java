package br.edu.ufrn.shipping.saga.processor.command;

public record Command(
    CommandType type,
    String orderId,
    String address
) {}
