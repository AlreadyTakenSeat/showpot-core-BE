package org.example.message;

import com.example.pub.message.TicketingAlertsToReserveServiceMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TicketingReservationInfraMessage(
    UUID userId,
    String name,
    UUID showId,
    String ticketingAt,
    List<String> addAlertAts,
    List<String> deleteAlertAts
) {

    public static TicketingReservationInfraMessage from(
        TicketingAlertsToReserveServiceMessage message
    ) {
        return TicketingReservationInfraMessage.builder()
            .userId(message.userId())
            .name(message.name())
            .showId(message.showId())
            .ticketingAt(message.ticketingAt().toString())
            .addAlertAts(message.addAts().stream().map(LocalDateTime::toString).toList())
            .deleteAlertAts(message.deleteAts().stream().map(LocalDateTime::toString).toList())
            .build();
    }
}
