package org.example.message;

import com.example.pub.message.TicketingAlertsToReserveServiceMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TicketingReservationInfraMessage(
    String userFcmToken,
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
            .userFcmToken(message.userFcmToken())
            .name(message.name())
            .showId(message.showId())
            .ticketingAt(message.ticketingAt().toString())
            .addAlertAts(message.addAts().stream().map(LocalDateTime::toString).toList())
            .deleteAlertAts(message.deleteAts().stream().map(LocalDateTime::toString).toList())
            .build();
    }
}
