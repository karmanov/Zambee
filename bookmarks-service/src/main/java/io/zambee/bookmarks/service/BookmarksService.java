package io.zambee.bookmarks.service;


import io.zambee.bookmarks.domain.Bookmark;
import io.zambee.bookmarks.dto.Article;
import io.zambee.bookmarks.dto.ArticleParseRequest;
import io.zambee.bookmarks.dto.BookmarksReportDTO;
import io.zambee.bookmarks.dto.CollectionDTO;
import io.zambee.bookmarks.repository.BookmarkRepository;
import io.zambee.bookmarks.repository.BookmarksInMemoryRepository;
import io.zambee.bookmarks.service.client.CrawlerClient;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookmarksService {

    @Autowired
    private BookmarksInMemoryRepository repository;

//    private CrawlerClient crawlerClient;

    @Autowired
    private BookmarkRepository bookmarkRepository;

//    @Autowired
//    public BookmarksService(BookmarksInMemoryRepository repository, CrawlerClient crawlerClient,
//                            BookmarkRepository bookmarkRepository) {
//        this.crawlerClient = crawlerClient;
//        this.repository = repository;
//        this.bookmarkRepository = bookmarkRepository;
//    }

//    public Article getBookmarkText(ArticleParseRequest request) {
//        return crawlerClient.crawleArticle(request);
//    }

    public List<Bookmark> findAll() {
        return bookmarkRepository.findAll();
//        return repository.findAll();
    }

    public BookmarksReportDTO saveBookmarksFromFile(MultipartFile file) {
        try {
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            Document document = Jsoup.parse(inputStream, "UTF-8", "http://example.com");
            List<Bookmark> bookmarks = parseDocument(document);
//            Map<String, Integer> duplicates = findDuplicates(bookmarks);
            log.info("Processed {} bookmarks", bookmarks.size());
            return new BookmarksReportDTO(bookmarks, Collections.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BookmarksReportDTO(Collections.emptyList(), Collections.emptyMap());
    }

    private void setBookmarksStatus(List<Bookmark> bookmarks) {
        log.info("Checking status for {} bookmarks started in {}", bookmarks.size());
        long startTime = DateTime.now().getMillis();
        bookmarks.forEach(b -> {
            int i = checkUrlStatus(b.getHref());
            b.setLastUpdateOn(DateTime.now());
            b.setStatusCode(i);
        });
        long duration = DateTime.now().getMillis() - startTime;
        log.info("Checking status for {} bookmarks finished in {}", bookmarks.size(), duration);
    }

    private List<Bookmark> parseDocument(Document document) {
        return document.select("a").stream()
                       .map(this::toBookmark)
                       .collect(Collectors.toList());
    }

    private Bookmark toBookmark(Element e) {
        Bookmark bookmark = new Bookmark();
        bookmark.setId(UUID.randomUUID());
        bookmark.setAddDate(unixTimeToDate(e.attr("ADD_DATE")));
        bookmark.setHref(e.attr("HREF"));
        bookmark.setIcon(e.attr("ICON"));
        bookmark.setName(e.text());
        return bookmark;
    }

    private DateTime unixTimeToDate(String sUnixTime) {
        long l = Long.parseLong(sUnixTime);
        return new DateTime(l * 1000);
    }

    private Map<String, Integer> findDuplicates(List<Bookmark> bookmarks) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> result = new HashMap<>();

        for (Bookmark bookmark : bookmarks) {
            Integer count = map.get(bookmark.getHref());
            map.put(bookmark.getHref(), (count == null) ? 1 : count + 1);
        }

        map.entrySet().stream().filter(entry -> entry.getValue() > 1).forEach(entry -> {
            result.put(entry.getKey(), entry.getValue());
        });
        return result;
    }

    private int checkUrlStatus(String url) {
        try {
            URL u = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("HEAD");  //OR  huc.setRequestMethod ("HEAD");
            huc.connect();
            int code = huc.getResponseCode();
            return code;
        } catch (IOException e) {
            log.warn("Could not get status for url {}. Reason: {}", url, e.getMessage());
            return 0;
        }
    }
}