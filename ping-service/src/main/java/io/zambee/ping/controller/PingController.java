package io.zambee.ping.controller;

import io.zambee.api.dto.ping.PingMessage;
import io.zambee.api.dto.pong.PongResponseDTO;
import io.zambee.ping.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/ping")
public class PingController {

    @Autowired
    private PingService pingService;

    @RequestMapping(method = RequestMethod.GET, value = "/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PingMessage ping(@PathVariable("message") String messages) {
        return pingService.ping(messages);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ping-pong")
    public Collection<PongResponseDTO> pingpong() {
        return pingService.getAllPongMessages();
    }
}
