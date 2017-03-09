package io.zambee.pong.controller;


import io.zambee.api.client.pong.PongApi;
import io.zambee.api.dto.pong.PongResponseDTO;
import io.zambee.pong.service.PongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class PongController implements PongApi {

    private final PongService pongService;

    @Autowired
    public PongController(PongService pongService) {
        this.pongService = pongService;
    }

    @Override
    public Collection<PongResponseDTO> getAllMessages() {
        return pongService.getAllPongMessages();
    }
}
