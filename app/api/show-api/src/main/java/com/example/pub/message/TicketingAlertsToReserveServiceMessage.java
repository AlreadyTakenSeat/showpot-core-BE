package com.example.pub.message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import org.example.dto.usershow.response.TicketingAlertsDomainResponse;

@Builder
public record TicketingAlertsToReserveServiceMessage(
    String userFcmToken,
    String name,
    UUID showId,
    LocalDateTime ticketingAt,
    List<LocalDateTime> addAts,
    List<LocalDateTime> deleteAts
) {

    public static TicketingAlertsToReserveServiceMessage of(
        TicketingAlertsDomainResponse response,
        String userFcmToken
    ) {
        return TicketingAlertsToReserveServiceMessage.builder()
            .userFcmToken(userFcmToken)
            .name(response.name())
            .showId(response.showId())
            .ticketingAt(response.ticketingAt())
            .addAts(response.addAts())
            .deleteAts(response.deleteAts())
            .build();
    }
}
