package br.edu.ufrn.shipping.saga.processor;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.edu.ufrn.shipping.exception.ShippingRefusedException;
import br.edu.ufrn.shipping.saga.processor.command.Command;
import br.edu.ufrn.shipping.saga.processor.event.Event;
import br.edu.ufrn.shipping.saga.processor.event.EventType;
import br.edu.ufrn.shipping.service.ShippingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@Profile("orchestration")
public class CommandProcessor {
    
    private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);

    @Autowired
    private ShippingService shippingService;


    @Bean
    public Function<Flux<Command>, Flux<Event>> processShippingCommand() {
        return flux -> flux
            .concatMap(this::process);
    }

    private Mono<Event> process(Command command) {
        return switch (command.type()) {
            case ACCEPT_SHIPPING -> acceptShipping(command);
        };
    }

    private Mono<Event> acceptShipping(Command command) {
        return shippingService
            .send(command.orderId(), command.address())
            .map(id -> new Event(
                EventType.SHIPPING_ACCEPTED,
                command.orderId(),
                id,
                command.address()))
            .doOnSuccess(shippingEvent -> logger.info("Shipping accepted: {}", shippingEvent))
            .onErrorResume(
                ShippingRefusedException.class, e -> Mono.just(
                    new Event(
                        EventType.SHIPPING_REFUSED,
                        command.orderId(),
                        null,
                        command.address())));
    }

}
