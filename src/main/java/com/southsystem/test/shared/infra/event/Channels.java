package com.southsystem.test.shared.infra.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    public static final String CUSTOMERS = "customers";

    @Output
    MessageChannel lines();

    @Input(CUSTOMERS)
    SubscribableChannel customers();

}
