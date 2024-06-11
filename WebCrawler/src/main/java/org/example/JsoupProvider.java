package org.example;

import java.io.IOException;

public interface JsoupProvider {
    void connect(String url) throws IOException;

    <T> T select(String selector);
}
