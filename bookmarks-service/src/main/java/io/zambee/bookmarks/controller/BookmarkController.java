package io.zambee.bookmarks.controller;

import io.zambee.bookmarks.dto.BookmarksReportDTO;
import io.zambee.bookmarks.service.BookmarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        return bookmarksService.processFile(file);
    }

}
