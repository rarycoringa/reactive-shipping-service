package br.edu.ufrn.shipping.saga.orchestration.command;

public record Command(
    CommandType type,
    String orderId,
    String address
) {}
