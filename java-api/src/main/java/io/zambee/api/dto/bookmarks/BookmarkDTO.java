package io.zambee.api.dto.bookmarks;

import lombok.*;
import org.joda.time.DateTime;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"addDate", "name"})
public class BookmarkDTO {

    private UUID id;

    private String href;

    private DateTime addDate;

    private String icon;

    private String name;

    private int statusCode;

    private DateTime lastUpdateOn;

    private String text;
}
