package com.southsystem.test.shared.reader;

import com.southsystem.test.shared.exception.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DirectoryReader {

    private static final String DIR = "/home/eduardo/data/in";

    @Autowired
    private FileReader fileReader;


    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryReader.class);

    public Flux<Void> readFiles() {
        return getFiles()
                .map(Path::toString)
                .flatMap(fileReader::readFile)
                .subscribeOn(Schedulers.elastic())
                ;
    }


    private Flux<Path> getFiles() {
        return Mono.fromCallable(() -> Files.list(Paths.get(DIR)))
                .onErrorMap(this::mapToTechnicalException)
                .flatMapMany(Flux::fromStream)
                ;
    }

    private TechnicalException mapToTechnicalException(Throwable exception) {
        LOGGER.error("An error happened when it was listing files from folder {}", DIR, exception);

        return new TechnicalException("Error to list files from in folder");
    }
}