package io.zambee.pong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"io.zambee"})
public class PongServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PongServiceApplication.class, args);
    }
}
