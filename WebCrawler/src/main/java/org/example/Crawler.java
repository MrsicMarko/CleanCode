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
    private Set<String> visitedUrls = new HashSet<>();

    public Crawler(String url, int depth, String domain) {
        this.url = url;
        this.depth = depth;
        this.domain = domain;
    }

    public void startCrawling() throws IOException {
        crawl(0, this.url, "->");
        System.out.println("Crawling completed.");
    }

    private void crawl(int depth, String url, String indent) {
        if (depth > this.depth || visitedUrls.contains(url) || !url.contains(this.domain)) {
            return;
        }
        visitedUrls.add(url);

        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println(indent + "Broken link: " + url);
            return;
        }

        System.out.println(indent + "URL: " + url);

        crawlHeadings(document, "h1", indent);
        crawlFurtherLinks(document, indent, depth);
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

    private void crawlFurtherLinks(Document document, String indent, int depth) {
        Elements links = document.select("a[href]");
        for (Element link : links) {
            String nextUrl = link.absUrl("href");
            if (!nextUrl.isEmpty()) {
                crawl(depth + 1, nextUrl, "--" + indent);
            }
        }
    }
}
