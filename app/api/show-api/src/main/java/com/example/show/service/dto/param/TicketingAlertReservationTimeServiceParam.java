package com.example.show.service.dto.param;

import lombok.Builder;

@Builder
public record TicketingAlertReservationTimeServiceParam(
    int beforeMinutes,
    boolean isReserved,
    boolean canReserve
) {

}
