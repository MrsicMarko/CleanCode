package org.example;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JsoupWrapperTest {
    static CrawlerWrapper jsoupWrapper;
    static Element mockElement;
    static Elements mockHeaderElements;
    static Elements mockLinkElements;

    @BeforeEach
    public void setup() {
        jsoupWrapper = new JsoupWrapper();
        mockElement = mock(Element.class);
        mockHeaderElements = new Elements(mockElement);
        mockLinkElements = new Elements(mockElement);
    }

    @Test
    void testGetHeadersList() {
        when(mockElement.tagName()).thenReturn("h1");
        when(mockElement.text()).thenReturn("Header");

        jsoupWrapper = new JsoupWrapper(mockHeaderElements, mockLinkElements);

        ArrayList<Header> headerList = jsoupWrapper.getHeadersList();

        assertEquals(1, headerList.size());
        assertEquals("Header", headerList.get(0).getHeaderString());
    }


    @Test
    void testGetLinkList() {
        when(mockElement.attr("abs:href")).thenReturn("https://example.com");

        jsoupWrapper = new JsoupWrapper(mockHeaderElements, mockLinkElements);

        ArrayList<String> linkList = jsoupWrapper.getLinkList();

        assertEquals(1, linkList.size());
        assertEquals("https://example.com", linkList.get(0));
    }
}
