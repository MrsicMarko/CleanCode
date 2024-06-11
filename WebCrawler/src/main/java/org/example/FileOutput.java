package org.example;

import java.io.IOException;

public interface FileOutput {
    void writeBeginning(Page page) throws IOException;

    void writeBody(Page page) throws Exception;

    void closeFile() throws IOException;

    void closeWriter() throws IOException;
}
