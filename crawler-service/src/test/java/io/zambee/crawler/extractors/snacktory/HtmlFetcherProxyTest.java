package io.zambee.crawler.extractors.snacktory;

import static org.junit.Assert.assertEquals;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import io.zambee.crawler.extractors.snacktory.HtmlFetcher;
import org.junit.Test;

public class HtmlFetcherProxyTest {

    public HtmlFetcherProxyTest() {
    }

    @Test
    public void testSocksProxy() {
        HtmlFetcher fetcher = new HtmlFetcher();
        Proxy proxy = new Proxy(Type.valueOf("SOCKS"), new InetSocketAddress("127.0.0.1", 3128));
        fetcher.setProxy(proxy);

        assertEquals("Invalid SOCKS proxy type name", "SOCKS", fetcher.getProxy().type().name());
    }

    @Test
    public void testNoProxy() {
        HtmlFetcher fetcher = new HtmlFetcher();
        assertEquals("HtmlFetch proxy server was not a NO_PROXY proxy", Proxy.NO_PROXY, fetcher.getProxy());
    }


}
