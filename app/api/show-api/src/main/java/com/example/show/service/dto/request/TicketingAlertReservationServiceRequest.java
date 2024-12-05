package com.example.show.service.dto.request;

import com.example.show.controller.vo.TicketingApiType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import org.example.dto.usershow.request.TicketingAlertReservationDomainRequest;

@Builder
public record TicketingAlertReservationServiceRequest(

    UUID userId,
    UUID showId,
    TicketingApiType type,
    List<LocalDateTime> alertAts
) {

    public TicketingAlertReservationDomainRequest toDomainRequest(
        String name,
        LocalDateTime ticketingAt
    ) {
        return TicketingAlertReservationDomainRequest.builder()
            .userId(userId)
            .showId(showId)
            .type(type.toDomainType())
            .name(name)
            .ticketingAt(ticketingAt)
            .alertAts(getAlertAts(ticketingAt))
            .build();
    }

    private List<LocalDateTime> getAlertAts(LocalDateTime ticketingAt) {
        return alertAts.stream()
            .filter(alertTime -> alertTime.isBefore(ticketingAt))
            .toList();
    }
}
