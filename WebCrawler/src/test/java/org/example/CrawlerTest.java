package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;


import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class CrawlerTest {

    String url = "http://projects.htl-klu.at/Projekt_2324/";
    String domain = "";
    ByteArrayOutputStream outContent;
    PrintStream originalOut;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeEach
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));

    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testUserInputInvalidURL() {
        assertFalse(Main.isValidURL("FixKaValideURL"));
    }

    @Test
    void testUserInputValidURL() {
        assertTrue(Main.isValidURL(url));
    }

    @Test
    void testUserInputInvalidDepth() {
        thrown.expect(NumberFormatException.class);
        Main.processDepthInput("depth");
    }

    @Test
    void testUserInputValidDepth() {
        int expectedDepth = 3;
        int actualDepth = Main.processDepthInput("3");
        assertEquals(expectedDepth, actualDepth);
    }

    @Test
    void testSuccessfulCrawling() throws IOException {
        Main.crawlWithUserInput(url, 0, domain);
        String printedContent = outContent.toString();
        assertTrue(printedContent.contains("Crawling completed."));
    }

    @Test
    void testCrawlWithInvalidURL() {
        Main.crawlWithUserInput("a", 0, domain);
        String printedContent = outContent.toString();
        assertTrue(printedContent.contains("Invalid URL."));
    }
}