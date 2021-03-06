package io.zambee.api.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CollectionDTO<T> {

    private List<T> items;
}
