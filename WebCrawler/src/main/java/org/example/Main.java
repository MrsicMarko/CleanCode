package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int depth = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter URL to crawl (entire URL, with http://..): ");
        String url = scanner.nextLine();
        System.out.print("Enter depth of crawling: ");
        String depthInput = scanner.nextLine();
        System.out.print("Enter domain to allow crawling: ");
        String domain = scanner.nextLine();

        scanner.close();

        try {
            depth = Integer.parseInt(depthInput);
            Crawler crawler = new Crawler(url, depth, domain);
            crawler.startCrawling();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}