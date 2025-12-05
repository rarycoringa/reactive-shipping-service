package br.edu.ufrn.shipping.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufrn.shipping.model.Shipping;
import br.edu.ufrn.shipping.repository.ShippingRepository;
import reactor.core.publisher.Mono;

@Service
public class ShippingService {
    @Autowired
    private ShippingRepository shippingRepository;
        
    private static final Logger logger = LoggerFactory.getLogger(ShippingService.class);

    public Mono<String> send(
        String orderId,
        String address
    ) {
        return shippingRepository
            .save(new Shipping(orderId, address))
            .map(Shipping::getId)
            .doOnSuccess(id -> logger.info("Shipping successfully sent: id={}, orderId={}", id, orderId));
    }
    
}
