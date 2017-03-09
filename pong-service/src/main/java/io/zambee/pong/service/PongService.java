package io.zambee.pong.service;

import io.zambee.api.dto.pong.PongResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
@Slf4j
public class PongService {

    private Collection<PongResponseDTO> storage;

    @PostConstruct
    public void init() {
        storage = new ArrayList<>();
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
        storage.add(new PongResponseDTO(UUID.randomUUID(), DateTime.now()));
    }

    public Collection<PongResponseDTO> getAllPongMessages() {
        return new ArrayList<>(storage);
    }


}
