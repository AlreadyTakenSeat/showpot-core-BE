package org.example.dto.viewcount;

import java.util.UUID;

public record ShowViewCountEvent(UUID showId) {

    public static ShowViewCountEvent from(UUID showId) {
        return new ShowViewCountEvent(showId);
    }
}
