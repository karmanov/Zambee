package io.zambee.crawler.comparator;


import io.zambee.api.dto.crawler.ImageResult;

import java.util.Comparator;

public class ImageComparator implements Comparator<ImageResult> {

    @Override
    public int compare(ImageResult o1, ImageResult o2) {
        // Returns the highest weight first
        return o2.weight.compareTo(o1.weight);
    }
}
