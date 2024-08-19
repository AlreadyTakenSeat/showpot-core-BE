package org.example.message;

import com.example.publish.message.TicketingAlertsToReserveServiceMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TicketingReservationInfraMessage(
    String userFcmToken,
    String name,
    UUID showId,
    List<String> addAts,
    List<String> deleteAts
) {

    public static TicketingReservationInfraMessage from(
        TicketingAlertsToReserveServiceMessage message
    ) {
        return TicketingReservationInfraMessage.builder()
            .userFcmToken(message.userFcmToken())
            .name(message.name())
            .showId(message.showId())
            .addAts(message.addAts().stream().map(LocalDateTime::toString).toList())
            .deleteAts(message.deleteAts().stream().map(LocalDateTime::toString).toList())
            .build();
    }
}