package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CrawlerThreadTest {

    @Test
    void testRun() throws InterruptedException {
        int threadCount = 3;
        CountDownLatch cdl = new CountDownLatch(threadCount);

        CrawlerThread ct;
        for (int i = 0; i < threadCount; i++) {
            ct = new CrawlerThread(1, "https://example.com", cdl);
            ct.start();
        }
        boolean completed = cdl.await(3L, TimeUnit.SECONDS);
        assertTrue(completed);
    }

    @AfterAll
    static void clearFileAfterTest() throws IOException {
        ReportWriter.clearFile("./report.md");
    }
}
