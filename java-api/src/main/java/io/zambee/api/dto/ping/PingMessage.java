package io.zambee.api.dto.ping;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PingMessage {

    private UUID id;

    private String message;
}
