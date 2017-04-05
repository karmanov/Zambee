package io.zambee.ping.service.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.zambee.api.client.pong.PongApi;
import io.zambee.api.dto.pong.PongResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
public class PongClient {

    private PongApi pongApi;

    @Autowired
    public PongClient(PongApi pongApi) {
        this.pongApi = pongApi;
    }

    @HystrixCommand(fallbackMethod = "getAllPongMessagesFallback")
    public Collection<PongResponseDTO> getAllPongMessages() {
        log.info("About to get all messages from pong-service");
        return pongApi.getAllMessages();
    }

    public Collection<PongResponseDTO> getAllPongMessagesFallback() {
        log.warn("Error during fetching messages from pong-service. Fallback in work");
        return Collections.emptyList();
    }


}
