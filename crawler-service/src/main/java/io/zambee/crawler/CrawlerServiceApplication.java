package io.zambee.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CrawlerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlerServiceApplication.class, args);
    }
}
