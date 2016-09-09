package io.zambee.crawler.service;

import io.zambee.crawler.dto.ArticleParseRequest;
import io.zambee.crawler.dto.ArticleParseResponse;
import io.zambee.crawler.dto.JResult;
import io.zambee.crawler.extractors.HtmlFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleParseService {

    public ArticleParseResponse parse(ArticleParseRequest request) {
        try {
            HtmlFetcher fetcher = new HtmlFetcher();
            JResult res = fetcher.fetchAndExtract(request.getUrl(), 10000, true);
            return new ArticleParseResponse(request.getUrl(), res.getTitle(), res.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArticleParseResponse();
    }


}
