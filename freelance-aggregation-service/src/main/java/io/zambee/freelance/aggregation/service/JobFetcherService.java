package io.zambee.freelance.aggregation.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class JobFetcherService {

    public void fetch() {
        try {
            Document document = Jsoup.connect("https://www.upwork.com/o/jobs/browse/?q=java").get();
            processDocument(document);
        } catch (IOException e) {
            log.error("Error during fetching jobs. Reason: {}", e.getMessage());
        }
    }

    private void processDocument(Document document) {
        Elements elementsByClass = document.getElementsByClass("job-tile");
        elementsByClass.stream().peek(e -> processElement(e));
        log.info("Found {} jobs on the first page", elementsByClass.size());
    }

    private void processElement(Element element) {
        Elements possibleTitles = element.getElementsByClass("break visited");
        Element first = possibleTitles.first();
        log.info(first.text());
    }

    public static void main(String[] args) {
        JobFetcherService jobFetcherService = new JobFetcherService();
        jobFetcherService.fetch();
    }
}
