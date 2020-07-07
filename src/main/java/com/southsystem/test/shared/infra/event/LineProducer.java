package com.southsystem.test.shared.infra.event;

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

    public LineProducer(Channels channels) {
        this.channels = channels;
    }


    public Mono<Void> sendMessage(String line) {

        Message<String> message = MessageBuilder.withPayload(line)
                                                .setHeader("type", extractLineType(line))
                                                .build();

        LOGGER.info("Enviando mensagem: {}", line);

        return Mono.fromCallable(() -> channels.lines().send(message))
                .then();


    }

    private String extractLineType(String line) {
        return line.split(DELIMITER)[0];
    }
}
