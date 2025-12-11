package br.edu.ufrn.shipping.saga.choreography;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.edu.ufrn.shipping.saga.choreography.event.EventType;
import br.edu.ufrn.shipping.saga.choreography.event.OrderEvent;
import br.edu.ufrn.shipping.saga.choreography.event.PaymentEvent;
import br.edu.ufrn.shipping.saga.choreography.event.ProductEvent;
import br.edu.ufrn.shipping.saga.choreography.event.ShippingEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Configuration
@Profile("choreography")
public class Choreographer {
    private static final Logger logger = LoggerFactory.getLogger(Choreographer.class);

    @Bean
    public Function<Flux<PaymentEvent>, Flux<ShippingEvent>> processPaymentEvent() {
        return flux -> flux
            .doOnNext(event -> logger.info("Received payment event: {}", event))
            .concatMap(this::handlePaymentEvent)
            .concatMap(this::process)
            .doOnNext(event -> logger.info("Sending shipping event: {}", event));
    }

    private Mono<ShippingEvent> handlePaymentEvent(PaymentEvent event) {
        return switch (event.type()) {
            case PAYMENT_CHARGED -> Mono.just(new ShippingEvent(EventType.SHIPPING_ACCEPTED));
            case PAYMENT_REFUSED, PAYMENT_REFUNDED -> Mono.empty();
            default -> Mono.empty();
        };
    }

    private Mono<ShippingEvent> process(ShippingEvent event) {
        return switch (event.type()) {
            case SHIPPING_ACCEPTED -> Mono.just(event)
                .doOnNext(e -> logger.info("Shipping accepted: {}", e));
                
            case SHIPPING_REFUSED -> Mono.just(event)
                .doOnNext(e -> logger.info("Shipping refused: {}", e));
                
            default -> Mono.empty();
        };
    }
}
