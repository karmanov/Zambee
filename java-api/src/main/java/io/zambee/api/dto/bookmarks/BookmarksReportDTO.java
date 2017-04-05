package io.zambee.api.dto.bookmarks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class BookmarksReportDTO {

    private List<BookmarkDTO> bookmarks;

    private Map<String, Integer> duplicates;
}
