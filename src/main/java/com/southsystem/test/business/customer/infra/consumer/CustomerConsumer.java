package com.southsystem.test.business.customer.infra.consumer;

import com.southsystem.test.shared.infra.event.Channels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Channels.class)
public class CustomerConsumer extends AbstractConsumer{

    @StreamListener(target = Channels.CUSTOMERS, condition = "headers['type']=='" + ENTITY_CUSTOMER + "'")
    public void consumer(final String message) {
        System.out.println("Consumindo mensagem de consumidor: " + message);
    }
}
