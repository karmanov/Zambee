package io.zambee.bookmarks.service;


import io.zambee.api.dto.bookmarks.BookmarkDTO;
import io.zambee.api.dto.bookmarks.BookmarksReportDTO;
import io.zambee.bookmarks.domain.Bookmark;
import io.zambee.bookmarks.repository.BookmarksInMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static io.zambee.bookmarks.utils.DateTimeUtils.unixTimeToDate;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class BookmarksService {

    private BookmarksInMemoryRepository bookmarksInMemoryRepository;

    public BookmarksService(BookmarksInMemoryRepository bookmarksInMemoryRepository) {
        this.bookmarksInMemoryRepository = bookmarksInMemoryRepository;
    }

    public BookmarksReportDTO saveBookmarksFromFile(MultipartFile file) {
        List<BookmarkDTO> bookmarks = Collections.emptyList();
        Map<String, Integer> duplicates = Collections.emptyMap();
        try {
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            Document document = Jsoup.parse(inputStream, "UTF-8", "http://example.com");
            bookmarks = parseDocument(document).stream()
                                               .map(this::toDTO)
                                               .collect(toList());
            duplicates = findDuplicates(bookmarks);
            log.info("Processed {} bookmarks", bookmarks.size());
        } catch (IOException e) {
            log.error("Could not parse bookmarks file. Reason {}", e.getMessage());
        }
        return buildResponseDTO(bookmarks, duplicates);
    }

    public HttpStatus getBookmarkStatus(UUID id) {
        Bookmark b = bookmarksInMemoryRepository.findById(id);
        int i = checkUrlStatus(b.getHref());
        return HttpStatus.valueOf(i);
    }

    public List<BookmarkDTO> findAll() {
        return bookmarksInMemoryRepository.findAll().stream()
                                          .map(this::toDTO)
                                          .collect(toList());
    }

    private BookmarksReportDTO buildResponseDTO(List<BookmarkDTO> bookmarks, Map<String, Integer> duplicates) {
        return new BookmarksReportDTO(bookmarks, duplicates);
    }

    private BookmarkDTO toDTO(Bookmark bookmark) {
        BookmarkDTO bookmarkDTO = new BookmarkDTO();
        BeanUtils.copyProperties(bookmark, bookmarkDTO);
        return bookmarkDTO;
    }

    private void setBookmarksStatus(List<Bookmark> bookmarks) {
        log.info("Checking status for {} bookmarks started in {}", bookmarks.size());
        long startTime = DateTime.now().getMillis();
        bookmarks.forEach(b -> {
            log.debug("Processing url: {}", b.getHref());
            int i = checkUrlStatus(b.getHref());
            b.setLastUpdateOn(DateTime.now());
            b.setStatusCode(i);
        });
        long duration = DateTime.now().getMillis() - startTime;
        log.info("Checking status for {} bookmarks finished in {}", bookmarks.size(), duration);
    }

    private List<Bookmark> parseDocument(Document document) {
        List<Bookmark> bookmarks = document.select("a").stream()
                                           .map(this::toBookmark)
                                           .collect(toList());
        bookmarksInMemoryRepository.saveAll(bookmarks);
        return bookmarks;
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

    private Map<String, Integer> findDuplicates(List<BookmarkDTO> bookmarks) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> result = new HashMap<>();

        for (BookmarkDTO bookmark : bookmarks) {
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
