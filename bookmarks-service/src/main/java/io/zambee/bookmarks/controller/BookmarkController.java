package io.zambee.bookmarks.controller;

import io.zambee.api.dto.bookmarks.BookmarkDTO;
import io.zambee.api.dto.bookmarks.BookmarksReportDTO;
import io.zambee.bookmarks.service.BookmarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/bookmarks")
public class BookmarkController {

    private BookmarksService bookmarksService;

    @Autowired
    public BookmarkController(BookmarksService bookmarksService) {
        this.bookmarksService = bookmarksService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BookmarksReportDTO uploadFile(@RequestParam("file") MultipartFile file) {
        return bookmarksService.saveBookmarksFromFile(file);
    }

    @RequestMapping(value = "/{id}/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus getById(@PathVariable UUID id) {
        return bookmarksService.getBookmarkStatus(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookmarkDTO> findAll() {
        return bookmarksService.findAll();
    }

}
