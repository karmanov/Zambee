package io.zambee.api.dto.pong;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PongResponseDTO {

    private UUID id;

    private DateTime createdOn;

}
