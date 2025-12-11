package br.edu.ufrn.shipping.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufrn.shipping.exception.ShippingRefusedException;
import br.edu.ufrn.shipping.model.Shipping;
import br.edu.ufrn.shipping.repository.ShippingRepository;
import reactor.core.publisher.Mono;

@Service
public class ShippingService {

    private static final Logger logger = LoggerFactory.getLogger(ShippingService.class);
    
    @Autowired
    private ShippingRepository shippingRepository;
        

    public Mono<String> accept(
        String orderId,
        String address
    ) {
        boolean refused = Math.random() < 0.1;

        if (refused) {
            return Mono.error(new ShippingRefusedException());
        }

        return shippingRepository
            .save(new Shipping(orderId, address))
            .map(Shipping::getId);
    }
    
}
