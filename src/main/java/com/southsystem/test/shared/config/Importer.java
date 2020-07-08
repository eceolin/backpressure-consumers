package com.southsystem.test.shared.config;

import com.southsystem.test.shared.reader.DirectoryReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class Importer {

    @Autowired
    private DirectoryReader directoryReader;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        directoryReader.readFiles()
                .blockLast(Duration.ofSeconds(30));
    }
}
