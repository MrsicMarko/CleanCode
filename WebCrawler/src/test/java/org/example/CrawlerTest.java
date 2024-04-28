package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CrawlerTest {
    String url = "http://projects.htl-klu.at/Projekt_2324/";
    String domain = "";
    Crawler validCrawler;

    @BeforeEach
    public void setUp() {
        validCrawler = new Crawler(url, 2, domain);
    }

    @AfterEach
    public void tearDown() {
        validCrawler = null;
    }

    @Test
    void testUserInputInvalidURL() {
        Crawler invalidCrawler = new Crawler("fixAFalscheURL", 1, "");
        assertThrows(IllegalArgumentException.class, () -> invalidCrawler.startCrawling());
    }

    @Test
    void testUserInputInvalidDepth() {
        //Crawler invalidCrawler = new Crawler(url, "d", "");
        //assertThrows(IllegalArgumentException.class, () -> invalidCrawler.startCrawling());
    }

    @Test
    void testStartCrawling() {
    }

    @Test
    void testCrawlHeadings() {
    }

    @Test
    void testCrawlFurtherLinks() {
    }

    @Test
    void testCrawl() {
    }

    @Test
    void testCrawlBadLink() {
    }
}