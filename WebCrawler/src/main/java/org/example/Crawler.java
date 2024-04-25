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
        //Elements headings = document.select("h1, h2, h3, h4, h5, h6");
        //for (Element heading : headings) {
        //    System.out.println(indent + "  " + heading.tagName() + ": " + heading.text());
        //}
        Elements headings1 = document.select("h1");
        /*
        for (int i = 1; i < 7; i++) {
            String head = "h" + i;
            Elements h = document.select(head);
            for (Element hd : h) {
                System.out.println(indent + "  ##" + hd.tagName() + ": " + hd.text());
            }
        }*/
        for (Element heading1 : headings1) {
            System.out.println(indent + "  #" + heading1.tagName() + ": " + heading1.text());
            Elements headings2 = document.select("h2");
            for (Element heading2 : headings2) {
                System.out.println(indent + "  ##" + heading2.tagName() + ": " + heading2.text());
                Elements headings3 = document.select("h3");
                for (Element heading3 : headings3) {
                    System.out.println(indent + "  ###" + heading3.tagName() + ": " + heading3.text());
                    Elements headings4 = document.select("h4");
                    for (Element heading4 : headings4) {
                        System.out.println(indent + "  ####" + heading4.tagName() + ": " + heading4.text());
                        Elements headings5 = document.select("h5");
                        for (Element heading5 : headings5) {
                            System.out.println(indent + "  #####" + heading5.tagName() + ": " + heading5.text());
                            Elements headings6 = document.select("h6");
                            for (Element heading6 : headings6) {
                                System.out.println(indent + "  ######" + heading6.tagName() + ": " + heading6.text());
                            }
                        }
                    }
                }
            }
        }
        //crawlHeadings(headings, indent);


        Elements links = document.select("a[href]");
        //crawlLinks(links, indent);
        for (Element link : links) {
            String nextUrl = link.absUrl("href");
            if (!nextUrl.isEmpty()) {
                crawl(depth + 1, nextUrl, "--" + indent);
            }
        }
    }

    private void crawlHeadings(Elements headings, String indent, Document document, String ints) {

        for (Element head : headings) {
            System.out.println(indent + "  ##" + head.tagName() + ": " + head.text());
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
