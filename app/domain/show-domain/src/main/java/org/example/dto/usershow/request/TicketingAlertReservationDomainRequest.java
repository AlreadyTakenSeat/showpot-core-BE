package org.example.dto.usershow.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import org.example.vo.TicketingType;

@Builder
public record TicketingAlertReservationDomainRequest(

    UUID userId,
    UUID showId,
    TicketingType type,
    String name,
    LocalDateTime ticketingAt,
    List<LocalDateTime> alertAts
) {

}
