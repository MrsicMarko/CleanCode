package org.example;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CrawlerThread extends Thread {
    private final String url;
    private final int maxDepth;
    private FileOutput file;
    private Page page;
    private CrawlerWrapper jsoupWrapper;
    private CountDownLatch countDownLatch;
    private static final Lock fileWriteLock;

    static {
        fileWriteLock = new ReentrantLock();
    }

    public CrawlerThread(int depth, String url) {
        this.maxDepth = depth;
        this.url = url;
    }

    public CrawlerThread(int depth, String url, CountDownLatch countDownLatch) {
        this.maxDepth = depth;
        this.url = url;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        this.page = new Page(url, 1);
        readPageRecursivelyFromJsoup(page);
        synchronizedFileWritingForThreads();
        try {
            file.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (countDownLatch != null)
            countDownLatch.countDown();
    }

    private void synchronizedFileWritingForThreads() {
        fileWriteLock.lock();

        setupWriter();
        writeToFile(page);

        fileWriteLock.unlock();
    }


    private void setupWriter() {
        try {
            file = new ReportWriter("./report.md");
            file.writeBeginning(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(Page page) {
        try {
            file.writeBody(page);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (page.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                writeToFile(subPage);
            }
        }
    }

    private void setUpJsoupWrapper() {
        jsoupWrapper = new JsoupWrapper();
    }

    private void readPageRecursivelyFromJsoup(Page page) {
        try {
            setUpJsoupWrapper();
            jsoupWrapper.readWebPage(page.getUrl());
            setPageElements(page);
            checkForDepthAndCallForNextDepth(page);
        } catch (Exception e) {
            page.setBroken(true);
            System.out.println("Broken Link detected: " + page.getUrl());
        }
    }

    private void setPageElements(Page page) {
        page.setHeaderStringList(jsoupWrapper.getHeadersList());
        page.setSubPages(jsoupWrapper.getLinkList());
    }

    private void checkForDepthAndCallForNextDepth(Page page) {
        if (page.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                readPageRecursivelyFromJsoup(subPage);
            }
        }
    }
}
