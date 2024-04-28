package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter URL to crawl (entire URL, with http://..): ");
        String url = scanner.nextLine();
        System.out.print("Enter depth of crawling: ");
        String depthInput = scanner.nextLine();
        System.out.print("Enter domain to allow crawling: ");
        String domain = scanner.nextLine();

        scanner.close();

        crawlWithUserInput(url, processDepthInput(depthInput), domain);
    }

    public static int processDepthInput(String depthInput) {
        int depth = 0;
        try {
            depth = Integer.parseInt(depthInput);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return depth;
    }

    public static void crawlWithUserInput(String url, int depth, String domain) {
        Crawler crawler = new Crawler(url, depth, domain);
        try {
            crawler.startCrawling();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}