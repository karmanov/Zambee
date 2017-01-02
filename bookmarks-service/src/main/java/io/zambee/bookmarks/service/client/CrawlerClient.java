package io.zambee.bookmarks.service.client;

import io.zambee.bookmarks.dto.Article;
import io.zambee.bookmarks.dto.ArticleParseRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("crawler-service")
public interface CrawlerClient {

    @RequestMapping(method = RequestMethod.POST, value = "/crawler/article/parse",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Article crawleArticle(@RequestBody ArticleParseRequest articleParseRequest);

}
