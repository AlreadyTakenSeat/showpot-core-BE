package com.example.show.service.dto.response;

import com.example.show.service.dto.param.TicketingAlertReservationTimeServiceParam;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.example.entity.usershow.TicketingAlert;

@Builder
public record TicketingAlertReservationServiceResponse(
    List<TicketingAlertReservationTimeServiceParam> times
) {

    public static TicketingAlertReservationServiceResponse as(
        LocalDateTime ticketingAt,
        List<TicketingAlert> reservedAlerts
    ) {
        List<TicketingAlertReservationTimeServiceParam> times = reservedAlerts.stream()
            .map(alert -> {
                int beforeMinutes = (int) Duration.between(alert.getAlertTime(), ticketingAt)
                    .toMinutes();

                boolean canReserve = alert.getAlertTime().isAfter(LocalDateTime.now());
                return TicketingAlertReservationTimeServiceParam.builder()
                    .beforeMinutes(beforeMinutes)
                    .isReserved(true)
                    .canReserve(canReserve)
                    .build();
            })
            .toList();

        return TicketingAlertReservationServiceResponse.builder()
            .times(times)
            .build();
    }
}
