package io.zambee.crawler.extractors.snacktory;


import io.zambee.crawler.utils.StringOperations;
import org.junit.Test;

import static org.junit.Assert.*;

public class SHelperTest {


    public SHelperTest() {
    }

    @Test
    public void testInnerTrim() {
        assertEquals("", StringOperations.innerTrim("   "));
        assertEquals("t", StringOperations.innerTrim("  t "));
        assertEquals("t t t", StringOperations.innerTrim("t t t "));
        assertEquals("t t", StringOperations.innerTrim("t    \nt "));
        assertEquals("t peter", StringOperations.innerTrim("t  peter "));
        assertEquals("t t", StringOperations.innerTrim("t    \n     t "));
    }

    @Test
    public void testCount() {
        assertEquals(1, StringOperations.count("hi wie &test; gehts", "&test;"));
        assertEquals(1, StringOperations.count("&test;", "&test;"));
        assertEquals(2, StringOperations.count("&test;&test;", "&test;"));
        assertEquals(2, StringOperations.count("&test; &test;", "&test;"));
        assertEquals(3, StringOperations.count("&test; test; &test; plu &test;", "&test;"));
    }

    @Test
    public void longestSubstring() {
//        assertEquals(9, ArticleTextExtractor.longestSubstring("hi hello how are you?", "hello how"));
        assertEquals("hello how", StringOperations.getLongestSubstring("hi hello how are you?", "hello how"));
        assertEquals(" people if ", StringOperations.getLongestSubstring("x now if people if todo?", "I know people if you"));
        assertEquals("", StringOperations.getLongestSubstring("?", "people"));
        assertEquals("people", StringOperations.getLongestSubstring(" people ", "people"));
    }

    @Test
    public void testHashbang() {
        assertEquals("sdfiasduhf+asdsad+sdfsdf#!", StringOperations.removeHashbang("sdfiasduhf+asdsad#!+sdfsdf#!"));
        assertEquals("sdfiasduhf+asdsad+sdfsdf#!", StringOperations.removeHashbang("sdfiasduhf+asdsad#!+sdfsdf#!"));
    }

    @Test
    public void testIsVideoLink() {
        assertTrue(StringOperations.isVideoLink("m.vimeo.com"));
        assertTrue(StringOperations.isVideoLink("m.youtube.com"));
        assertTrue(StringOperations.isVideoLink("www.youtube.com"));
        assertTrue(StringOperations.isVideoLink("http://youtube.com"));
        assertTrue(StringOperations.isVideoLink("http://www.youtube.com"));

        assertTrue(StringOperations.isVideoLink("https://youtube.com"));

        assertFalse(StringOperations.isVideoLink("test.com"));
        assertFalse(StringOperations.isVideoLink("irgendwas.com/youtube.com"));
    }

    @Test
    public void testExctractHost() {
        assertEquals("techcrunch.com",
                StringOperations.extractHost("http://techcrunch.com/2010/08/13/gantto-takes-on-microsoft-project-with-web-based-project-management-application/"));
    }

    @Test
    public void testFavicon() {
        assertEquals("http://www.n24.de/news/../../../media/imageimport/images/content/favicon.ico",
                StringOperations.useDomainOfFirstArg4Second("http://www.n24.de/news/newsitem_6797232.html", "../../../media/imageimport/images/content/favicon.ico"));
        StringOperations.useDomainOfFirstArg4Second("http://www.n24.de/favicon.ico", "/favicon.ico");
        StringOperations.useDomainOfFirstArg4Second("http://www.n24.de/favicon.ico", "favicon.ico");
    }

    @Test
    public void testFaviconProtocolRelative() throws Exception {
        assertEquals("http://de.wikipedia.org/apple-touch-icon.png",
                StringOperations.useDomainOfFirstArg4Second("http://de.wikipedia.org/favicon", "//de.wikipedia.org/apple-touch-icon.png"));
    }

    @Test
    public void testImageProtocolRelative() throws Exception {
        assertEquals("http://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_Greece.svg/150px-Flag_of_Greece.svg.png",
                StringOperations.useDomainOfFirstArg4Second("http://de.wikipedia.org/wiki/Griechenland", "//upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_Greece.svg/150px-Flag_of_Greece.svg.png"));
    }

    @Test
    public void testEncodingCleanup() {
        assertEquals("utf-8", StringOperations.encodingCleanup("utf-8"));
        assertEquals("utf-8", StringOperations.encodingCleanup("utf-8\""));
        assertEquals("utf-8", StringOperations.encodingCleanup("utf-8'"));
        assertEquals("test-8", StringOperations.encodingCleanup(" test-8 &amp;"));
    }

    @Test
    public void testUglyFacebook() {
        assertEquals("http://www.bet.com/collegemarketingreps&h=42263",
                StringOperations.getUrlFromUglyFacebookRedirect("http://www.facebook.com/l.php?u=http%3A%2F%2Fwww.bet.com%2Fcollegemarketingreps&h=42263"));
    }

    @Test
    public void testEstimateDate() {
        assertNull(StringOperations.estimateDate("http://www.facebook.com/l.php?u=http%3A%2F%2Fwww.bet.com%2Fcollegemarketin"));
        assertEquals("2010/02/15", StringOperations.estimateDate("http://www.vogella.de/blog/2010/02/15/twitter-android/"));
        assertEquals("2010/02", StringOperations.estimateDate("http://www.vogella.de/blog/2010/02/twitter-android/12"));
        assertEquals("2009/11/05", StringOperations.estimateDate("http://cagataycivici.wordpress.com/2009/11/05/mobile-twitter-client-with-jsf/"));
        assertEquals("2009", StringOperations.estimateDate("http://cagataycivici.wordpress.com/2009/sf/12/1/"));
        assertEquals("2011/06", StringOperations.estimateDate("http://bdoughan.blogspot.com/2011/06/using-jaxbs-xmlaccessortype-to.html"));
        assertEquals("2011", StringOperations.estimateDate("http://bdoughan.blogspot.com/2011/13/using-jaxbs-xmlaccessortype-to.html"));
    }

    @Test
    public void testCompleteDate() {
        assertNull(StringOperations.completeDate(null));
        assertEquals("2001/01/01", StringOperations.completeDate("2001"));
        assertEquals("2001/11/01", StringOperations.completeDate("2001/11"));
        assertEquals("2001/11/02", StringOperations.completeDate("2001/11/02"));
    }
}
