package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ReportWriterTest {
    String path = "writer_test.md";
    @Mock
    Page pageMock = mock(Page.class);
    Page page;
    ReportWriter reportWriter;
    FileReader reader;
    BufferedReader bufferedReader;

    @BeforeEach
    void setUp() throws IOException {
        reportWriter = new ReportWriter(path);
    }

    private void generateSamplePage() {
        page = new Page("https://orf.at/", 1);
        ArrayList<String> linkList = new ArrayList<>();
        linkList.add("https://orf.at/news");
        ArrayList<Header> headerList = new ArrayList<>();
        headerList.add(new Header("Sample Header", 3));
        page.setSubPages(linkList);
        page.setHeaderStringList(headerList);
    }

    @Test
    void writeBeginning() throws IOException {
        when(pageMock.getUrl()).thenReturn("https://orf.at/");
        reportWriter.writeBeginning(pageMock);
        reportWriter.closeWriter();
        String actual = readTest();
        String expected = "-----START OF FILE-----<br>input: <a>https://orf.at/ </a>\n";
        assertEquals(expected, actual);
    }


    @Test
    void writeBody() throws Exception {
        generateSamplePage();
        assertDoesNotThrow(() -> reportWriter.writeBody(page));
        reportWriter.closeWriter();
        String actual = readTest();
        String expected = "### ->Sample Header \n<br> -->link to <a>https://orf.at/news</a>\n\n";
        assertEquals(expected, actual);
    }

    @Test
    void closeFile() throws IOException {
        assertDoesNotThrow(() -> reportWriter.closeFile());
        String actual = readTest();
        String expected = "\n-----END OF FILE-----<br>\n";
        assertEquals(expected, actual);
    }

    private String readTest() throws IOException {
        reader = new FileReader(path);
        bufferedReader = new BufferedReader(reader);
        int i;
        String result = "";
        while ((i = bufferedReader.read()) != -1)
            result += (char) i;
        bufferedReader.close();
        return result;
    }

    @AfterEach
    void tearDown() {
        File f = new File(path);
        assertTrue(f.delete());
    }
}
