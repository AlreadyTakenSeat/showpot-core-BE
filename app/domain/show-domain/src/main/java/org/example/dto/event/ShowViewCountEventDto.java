package org.example.dto.event;

import java.util.UUID;

public record ShowViewCountEventDto(UUID showId) {

    public static ShowViewCountEventDto from(UUID showId) {
        return new ShowViewCountEventDto(showId);
    }
}
