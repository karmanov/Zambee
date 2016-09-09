package io.zambee.crawler.controller;


import io.zambee.crawler.dto.ArticleParseRequest;
import io.zambee.crawler.dto.ArticleParseResponse;
import io.zambee.crawler.service.ArticleParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/crawler")
@Slf4j
public class ArticleParseController {

    private ArticleParseService parseService;

    @Autowired
    public ArticleParseController(ArticleParseService parseService) {
        this.parseService = parseService;
    }

    @RequestMapping(value = "/article/parse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ArticleParseResponse extract(@RequestBody ArticleParseRequest articleParseRequest) {
        log.info("Processing URL {}", articleParseRequest.getUrl());
        return parseService.parse(articleParseRequest);
    }
}
