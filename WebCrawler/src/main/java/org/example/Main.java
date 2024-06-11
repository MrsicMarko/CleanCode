package org.example;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter URLs to crawl (separated by comma): ");
        String urlInputList = scanner.nextLine();
        System.out.print("Enter depth of crawling: ");
        String depthInput = scanner.nextLine();
        System.out.print("Enter domain to allow crawling: ");
        String domainInput = scanner.nextLine();

        scanner.close();


        int depth = processDepthInput(depthInput);
        List<String> urlList = processUrlInput(urlInputList);
        
        if (depth < 0 || urlList == null) {
            return;
        }

        crawlWithUserInput("urlInput", processDepthInput(depthInput), domainInput);
    }

    public static int processDepthInput(String depthInput) {
        int depth;
        try {
            depth = Integer.parseInt(depthInput);
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input -> Exiting with Error: " + e.getMessage());
            return -1;
        }
        return depth;
    }

    public static List<String> processUrlInput(String urlInput) {
        List<String> urls = new ArrayList<>();
        String[] urlArray = urlInput.split(",");
        for (String url : urlArray) {
            urls.add(url.trim());
        }
        if (urls.isEmpty()) {
            System.out.println("No URLs provided. Exiting...");
            return null;
        }

        return urls;
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