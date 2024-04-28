package org.example;

import java.net.MalformedURLException;
import java.net.URL;
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

    public static boolean isValidURL(String urlString) {
        try {
            new URL(urlString);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static void crawlWithUserInput(String url, int depth, String domain) {
        if (isValidURL(url)) {
            Crawler crawler = new Crawler(url, depth, domain);
            crawler.startCrawling();
        } else {
            System.out.println("Invalid URL.");
        }
    }
}