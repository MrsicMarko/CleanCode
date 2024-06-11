package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportWriter implements FileOutput {
    private final String path;
    private final FileWriter fileWriter;
    private Formatter formatter;

    public static void clearFile(String path) throws IOException {
        PrintWriter writer = new PrintWriter(path);
        writer.print("");
        writer.close();
    }

    public ReportWriter(String path) throws IOException {
        this.path = path;
        fileWriter = new FileWriter(this.path, true);
    }

    public void writeBeginning(Page page) throws IOException {
        fileWriter.write("<br>-----START OF FILE-----");
        fileWriter.write("<br>input: <a>" + page.getUrl() + " </a>");
        fileWriter.write("\n");
    }

    public void writeBody(Page page) throws Exception {
        getFormattedPage(page);
        fileWriter.write(formatter.getOutputString());
    }

    private void getFormattedPage(Page page) {
        formatter = new PageFormatter(page);
        formatter.generateOutputString();
    }

    public void closeFile() throws IOException {
        fileWriter.write("\n-----END OF FILE-----\n");
        fileWriter.close();
    }

    public void closeWriter() throws IOException {
        fileWriter.close();
    }
}
