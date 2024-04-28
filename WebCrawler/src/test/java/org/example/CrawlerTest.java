package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrawlerTest {

    @BeforeEach
    void setUp() {
        String url = "http://projects.htl-klu.at/Projekt_2324/";
        String domain = "";
        Crawler validCrawler = new Crawler(url, 2, domain);
    }

    @Test
    void testUserInputInvalidURL() {
    }

    @Test
    void testUserInputInvalidDepth() {
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