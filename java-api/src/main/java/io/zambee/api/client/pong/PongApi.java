package io.zambee.api.client.pong;

import io.zambee.api.dto.pong.PongResponseDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@FeignClient(name = "pong-service")
public interface PongApi {

    @RequestMapping(value = "/pong", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Collection<PongResponseDTO> getAllMessages();
}
