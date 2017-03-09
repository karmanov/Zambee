package io.zambee.ping.service;

import io.zambee.api.dto.ping.PingMessage;
import io.zambee.api.dto.pong.PongResponseDTO;
import io.zambee.ping.service.client.PongClient;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Slf4j
@Service
public class PingService {

    private static final String SERVER_SALT = "fCJsrCP2QaaH2aBC";

    private PongClient pongClient;

    @Autowired
    public PingService(PongClient pongClient) {
        this.pongClient = pongClient;
    }

    public PingMessage ping(String message) {
        String messageBody = new StringBuilder()
                .append(message).append(" | ")
                .append(SERVER_SALT).append(" | ")
                .append(DateTime.now()).toString();

        return new PingMessage(UUID.randomUUID(), messageBody);
    }

    public Collection<PongResponseDTO> getAllPongMessages() {
        log.info("About to make pong request");
        return pongClient.getAllPongMessages();
    }

}
