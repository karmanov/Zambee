package io.zambee.bookmarks.repository;

import io.zambee.bookmarks.domain.Bookmark;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BookmarkRepository extends MongoRepository<Bookmark, UUID> {
}
