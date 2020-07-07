package com.southsystem.test.shared.reader;

import java.util.stream.Stream;

public interface Reader {

    void process(Stream<String> line);

    boolean supports();
}
