package com.southsystem.test.shared.reader;

import com.southsystem.test.shared.infra.event.LineProducer;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.adapter.rxjava.RxJava2Adapter;
import reactor.adapter.rxjava.RxJava3Adapter;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;

import static reactor.adapter.rxjava.RxJava2Adapter.flowableToFlux;

@Component
public class FileReader {

    private final LineProducer lineProducer;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    public FileReader(final LineProducer lineProducer) {
        this.lineProducer = lineProducer;
    }

    public Mono<Void> readFile(String filePath) {
        Maybe<Void> single = Flowable.using(
                () -> new BufferedReader(new java.io.FileReader(filePath)),
                reader -> Flowable.fromIterable(() -> reader.lines().iterator()),
                reader -> reader.close())
                .flatMap(lineProducer::sendMessage)
                .singleElement();

        return RxJava2Adapter.maybeToMono(single);
    }
}
