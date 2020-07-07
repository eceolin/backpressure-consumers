package com.southsystem.test.customer.infra.consumer;

import com.southsystem.test.shared.infra.event.Channels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Channels.class)
public class CustomerConsumer {

    private static final String TYPE = "002";

    @StreamListener(target = Channels.CUSTOMERS, condition = "headers['type']=='" + TYPE + "'")
    public void consumer(final String message) {
        System.out.println("Consumindo mensagem de consumidor: " + message);
    }
}
