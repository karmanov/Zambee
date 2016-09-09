package io.zambee.crawler.extractors;

import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import static org.junit.Assert.*;


public class OutputFormatterTest {


    @Test
    public void testSkipHidden() {
        OutputFormatter formatter = new OutputFormatter();
        Document doc = Jsoup.parse("<div><div style=\"display:none\">xy</div>test</div>");
        StringBuilder sb = new StringBuilder();
        formatter.appendTextSkipHidden(doc, sb);
        assertEquals("test", sb.toString());
    }

    @Test
    public void testTextList() {
        OutputFormatter formatter = new OutputFormatter();
        Document doc = Jsoup.parse("<div><p><p>aa</p></p><p>bb</p><p>cc</p></div>");
        assertEquals(Arrays.asList("aa", "bb", "cc"), formatter.getTextList(doc));
    }
}
