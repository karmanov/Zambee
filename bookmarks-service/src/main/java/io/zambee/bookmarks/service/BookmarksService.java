package io.zambee.bookmarks.service;


import io.zambee.bookmarks.domain.Bookmark;
import io.zambee.bookmarks.dto.BookmarksReportDTO;
import io.zambee.bookmarks.dto.CollectionDTO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookmarksService {

    public BookmarksReportDTO processFile(MultipartFile file) {
        try {
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            Document document = Jsoup.parse(inputStream, "UTF-8", "http://example.com");
            List<Bookmark> bookmarks = parseDocument(document);
            Map<String, Integer> duplicates = findDuplicates(bookmarks);
            log.info("Processed {} bookmarks", bookmarks.size());
            return new BookmarksReportDTO(bookmarks, duplicates);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BookmarksReportDTO(Collections.emptyList(), Collections.emptyMap());
    }

    private List<Bookmark> parseDocument(Document document) {
        return document.select("a").stream()
                .map(this::toBookmark)
                .collect(Collectors.toList());
    }

    private Bookmark toBookmark(Element e) {
        Bookmark bookmark = new Bookmark();
        bookmark.setAddDate(unixTimeToDate(e.attr("ADD_DATE")));
        bookmark.setHref(e.attr("HREF"));
        bookmark.setIcon(e.attr("ICON"));
        bookmark.setName(e.text());
        return bookmark;
    }

    private Date unixTimeToDate(String sUnixTime) {
        long l = Long.parseLong(sUnixTime);
        return new Date(l * 1000);
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
}
