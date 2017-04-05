package io.zambee;

import io.zambee.crawler.extractors.readability.HttpPageReader;
import io.zambee.crawler.extractors.readability.PageReadException;
import io.zambee.crawler.extractors.readability.Readability;

/**
 * Created by dmytro.karmanov on 15/03/2017.
 */
public class App {

    public static void main(String[] args) throws PageReadException {
        Readability readability = new Readability();
        HttpPageReader httpPageReader = new HttpPageReader();
        readability.setPageReader(httpPageReader);
        readability.processDocument("https://habrahabr.ru/post/323570/");
    }
}
