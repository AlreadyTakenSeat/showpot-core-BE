package org.example.dto.usershow.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TicketingAlertsDomainResponse(
    String name,
    UUID showId,
    LocalDateTime ticketingAt,
    List<LocalDateTime> addAts,
    List<LocalDateTime> deleteAts
) {

}
