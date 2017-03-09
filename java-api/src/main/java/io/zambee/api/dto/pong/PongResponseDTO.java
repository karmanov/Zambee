package io.zambee.pong.dto;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Data
public class PongResponseDTO {

    private UUID id;

    private DateTime createdOn;

}
