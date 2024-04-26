package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Crawler {
    private String url, domain;
    private int depth;
    private Set<String> visited = new HashSet<>();

    public Crawler(String url, int depth, String domain) {
        this.url = url;
        this.depth = depth;
        this.domain = domain;
    }

    public void startCrawling() throws IOException {
        crawl(0, this.url, ">");
        System.out.println("Crawling completed.");
    }

    private void crawl(int depth, String url, String indent) {
        if (depth > this.depth || visited.contains(url) || !url.contains(this.domain)) {
            return;
        }
        visited.add(url);

        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println(indent + "Broken link: " + url);
            return;
        }

        System.out.println(indent + "URL: " + url);

        crawlHeadings(document, "h1", indent);

        Elements links = document.select("a[href]");
        //crawlLinks(links, indent);
        for (Element link : links) {
            String nextUrl = link.absUrl("href");
            if (!nextUrl.isEmpty()) {
                crawl(depth + 1, nextUrl, "--" + indent);
            }
        }
    }

    public static void crawlHeadings(Document document, String tag, String indent) {
        if (tag.equals("h7")) {
            return;
        }
        String newIndent = indent + "#";
        Elements headings = document.select(tag);
        for (Element heading : headings) {
            System.out.println(newIndent + heading.tagName() + ": " + heading.text());

            String nextTag = "h" + (Integer.parseInt(tag.substring(1)) + 1);

            crawlHeadings(document, nextTag, newIndent);
        }
    }

    private void crawlLinks(Elements links, String indent) {
        for (Element link : links) {
            String nextUrl = link.absUrl("href");
            if (!nextUrl.isEmpty()) {
                crawl(depth + 1, nextUrl, "--" + indent);
            }
        }
    }
}
