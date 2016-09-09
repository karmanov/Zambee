package io.zambee.bookmarks.dto;

import io.zambee.bookmarks.domain.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class BookmarksReportDTO {

    private List<Bookmark> bookmarks;

    private Map<String, Integer> duplicates;
}
