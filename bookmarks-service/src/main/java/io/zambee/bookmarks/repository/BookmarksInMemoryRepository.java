package io.zambee.bookmarks.repository;

import io.zambee.bookmarks.domain.Bookmark;
import io.zambee.bookmarks.exeption.BookmarkNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookmarksInMemoryRepository {

    private List<Bookmark> storage;

    @PostConstruct
    public void init() {
        storage = new ArrayList<>();
        storage.addAll(buildBookmarksList(10));
    }

    public Bookmark getById(UUID id) {
        return storage.stream().filter(b -> id.equals(b.getId()))
                      .findFirst().orElseThrow(BookmarkNotFoundException::new);
    }

    public void add(Bookmark bookmark) {
        storage.add(bookmark);
    }

    public List<Bookmark> findAll() {
        return new ArrayList<>(storage);
    }

    public void addAll(List<Bookmark> bookmarks) {
        storage.addAll(bookmarks);
    }

    private Bookmark buildBookmark() {
        UUID id = UUID.randomUUID();
        return Bookmark.builder()
                       .id(id)
                       .name("Name of " + id)
                       .href("href of " + id)
                       .build();
    }

    private List<Bookmark> buildBookmarksList(int size) {
        List result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(buildBookmark());
        }
        return result;
    }
}
