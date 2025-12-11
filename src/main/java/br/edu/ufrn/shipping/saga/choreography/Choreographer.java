package br.edu.ufrn.shipping.saga.choreography;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.edu.ufrn.shipping.saga.choreography.event.EventType;
import br.edu.ufrn.shipping.saga.choreography.event.PaymentEvent;
import br.edu.ufrn.shipping.saga.choreography.event.ShippingEvent;
import br.edu.ufrn.shipping.service.ShippingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@Profile("choreography")
public class Choreographer {
    
    private static final Logger logger = LoggerFactory.getLogger(Choreographer.class);

    @Autowired
    private ShippingService shippingService;

    @Bean
    public Function<Flux<PaymentEvent>, Flux<ShippingEvent>> processPaymentEvent() {
        return flux -> flux
            .doOnNext(event -> logger.info("Received payment event: {}", event))
            .concatMap(this::process)
            .doOnNext(event -> logger.info("Sending shipping event: {}", event));
    }

    private Mono<ShippingEvent> process(PaymentEvent event) {
        return switch (event.type()) {
            case PAYMENT_CHARGED -> shippingService.accept(event.orderId(), event.address())
                .thenReturn(new ShippingEvent(
                    EventType.SHIPPING_ACCEPTED,
                    event.orderId(),
                    event.address()))
                .onErrorReturn(new ShippingEvent(
                    EventType.SHIPPING_REFUSED,
                    event.orderId(),
                    event.address()));
                
            case PAYMENT_REFUSED, PAYMENT_REFUNDED -> Mono.empty();
                
            default -> Mono.empty();
        };
    }
}
