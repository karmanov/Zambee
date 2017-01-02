package io.zambee.bookmarks.domain;

import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"addDate", "name"})
public class Bookmark {

    @Id
    private UUID id;

    private String href;

    private DateTime addDate;

    private String icon;

    private String name;

    private int statusCode;

    private DateTime lastUpdateOn;

    private String text;
}
