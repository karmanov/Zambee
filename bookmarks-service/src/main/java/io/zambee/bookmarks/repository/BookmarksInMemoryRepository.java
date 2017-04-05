package io.zambee.bookmarks.repository;

import io.zambee.bookmarks.domain.Bookmark;

import io.zambee.bookmarks.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookmarksInMemoryRepository {

    private List<Bookmark> repo = new ArrayList<>();

    public void save(Bookmark bookmark) {
        repo.add(bookmark);
    }

    public void saveAll(List<Bookmark> bookmarks) {
        repo.addAll(bookmarks);
    }

    public void remove(Bookmark bookmark) {
        Bookmark b = findById(bookmark.getId());
        repo.remove(b);
    }

    public Bookmark findById(UUID id) {
        return repo.stream()
                   .filter(b -> Objects.equals(id, b.getId()))
                   .findFirst()
                   .orElseThrow(NotFoundException::new);
    }

    public Collection<Bookmark> findByIdIn(Set<UUID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        return repo.stream()
                   .filter(b -> ids.contains(b.getId()))
                   .collect(Collectors.toSet());
    }

    public Collection<Bookmark> findAll() {
        return new ArrayList<>(repo);
    }
}
