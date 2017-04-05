package io.zambee.crawler.service;

import io.zambee.api.dto.crawler.ArticleParseRequest;
import io.zambee.api.dto.crawler.ArticleParseResponse;
import io.zambee.api.dto.crawler.JResult;
import io.zambee.crawler.extractors.snacktory.HtmlFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ArticleParseService {

    public ArticleParseResponse parse(ArticleParseRequest request) {
        try {
            HtmlFetcher fetcher = new HtmlFetcher();
            JResult res = fetcher.fetchAndExtract(request.getUrl(), 10000, true);
            List<String> textList = res.getTextList();
            System.out.println("=====================");
            textList.forEach(System.out::println);
            System.out.println("=====================");
            return new ArticleParseResponse(request.getUrl(), res.getTitle(), res.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArticleParseResponse();
    }


}
