package io.zambee.bookmarks.service;

import io.zambee.api.dto.bookmarks.BookmarksReportDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookmarksServiceTest {

    @Autowired
    private BookmarksService bookmarksService;

    @Autowired
    private ResourceLoader resourceLoader;


    @Test
    public void processFile() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:bookmarks_6_5_15.html");
        File file = resource.getFile();
        InputStream targetStream = new FileInputStream(file);
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", targetStream);
        BookmarksReportDTO bookmarksReportDTO = bookmarksService.saveBookmarksFromFile(firstFile);
        assertEquals(4, bookmarksReportDTO.getBookmarks().size());
    }
}