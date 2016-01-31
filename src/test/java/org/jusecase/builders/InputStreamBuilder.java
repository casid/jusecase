package org.jusecase.builders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public class InputStreamBuilder implements Builder<InputStream> {
    private InputStream inputStream;

    public InputStreamBuilder() {
        withString("");
    }

    public InputStreamBuilder withString(String content) {
        inputStream = new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8")));
        return this;
    }

    public InputStream build() {
        return inputStream;
    }
}
