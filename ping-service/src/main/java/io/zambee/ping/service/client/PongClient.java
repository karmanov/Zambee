package io.zambee.ping.service.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.zambee.api.client.pong.PongApi;
import io.zambee.api.dto.pong.PongResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class PongClient {

    private PongApi pongApi;

    @Autowired
    public PongClient(PongApi pongApi) {
        this.pongApi = pongApi;
    }

    @HystrixCommand(fallbackMethod = "getAllPongMessagesFallback")
    public Collection<PongResponseDTO> getAllPongMessages() {
        return pongApi.getAllMessages();
    }

    public Collection<PongResponseDTO> getAllPongMessagesFallback() {
        return Collections.emptyList();
    }


}
