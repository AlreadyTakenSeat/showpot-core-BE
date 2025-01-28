package com.example.pub.message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import org.example.dto.usershow.response.TicketingAlertsDomainResponse;

@Builder
public record TicketingAlertsToReserveServiceMessage(
    UUID userId,
    String name,
    UUID showId,
    LocalDateTime ticketingAt,
    List<LocalDateTime> addAts,
    List<LocalDateTime> deleteAts
) {

    public static TicketingAlertsToReserveServiceMessage of(
        TicketingAlertsDomainResponse response,
        UUID userId
    ) {
        return TicketingAlertsToReserveServiceMessage.builder()
            .userId(userId)
            .name(response.name())
            .showId(response.showId())
            .ticketingAt(response.ticketingAt())
            .addAts(response.addAts())
            .deleteAts(response.deleteAts())
            .build();
    }
}
