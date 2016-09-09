package io.zambee.bookmarks.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(exclude = {"addDate", "name"})
public class Bookmark {

    private String href;

    private Date addDate;

    private String icon;

    private String name;
}
