package io.zambee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PingServiceApplication.class, args);
    }
}
