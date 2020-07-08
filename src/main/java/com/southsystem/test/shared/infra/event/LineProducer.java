package com.southsystem.test.shared.infra.event;

import com.southsystem.test.shared.exception.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@EnableBinding(Channels.class)
public class LineProducer {

    private static final String DELIMITER = "ç";

    private final Channels channels;

    private static final Logger LOGGER = LoggerFactory.getLogger(LineProducer.class);

    private static final String ERROR_SEND_KAFKA = "Error to send message to kafka topic";

    public LineProducer(Channels channels) {
        this.channels = channels;
    }

    public Mono<Void> sendMessage(String line) {
        return  Mono.just(line)
                .doOnNext(l -> LOGGER.info("Sending 'line' message to kafka: [{}]", l))
                .flatMap(this::send)
                .then();
    }

    private Mono<Boolean> send(String line) {
        Message<String> message = MessageBuilder.withPayload(line)
                .setHeader("type", extractLineType(line))
                .build();

        return Mono.fromCallable(() -> channels.lines().send(message))
                    .onErrorMap(this::logAndMapError)
                    .filter(Boolean::booleanValue)
                    .switchIfEmpty(Mono.error(new TechnicalException(ERROR_SEND_KAFKA)));

    }

    private String extractLineType(String line) {
        return line.split(DELIMITER)[0];
    }

    private TechnicalException logAndMapError(Throwable throwable) {
        LOGGER.error("Some error happened when was trying to send message to kafka topic lines", throwable);
        return new TechnicalException(ERROR_SEND_KAFKA);
    }
}
