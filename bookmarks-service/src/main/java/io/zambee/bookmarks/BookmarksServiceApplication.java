package io.zambee.bookmarks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BookmarksServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookmarksServiceApplication.class, args);
    }
}
