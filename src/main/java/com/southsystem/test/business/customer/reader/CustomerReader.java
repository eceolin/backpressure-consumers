package com.southsystem.test.business.customer.reader;

import com.southsystem.test.shared.reader.Reader;

import java.util.stream.Stream;

public class CustomerReader implements Reader {

    @Override
    public void process(Stream<String> line) {

    }

    @Override
    public boolean supports() {
        return false;
    }
}
