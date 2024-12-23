package com.example.show.controller.dto.request;

import com.example.show.controller.vo.TicketingApiType;
import com.example.show.service.dto.request.TicketingAlertReservationServiceRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import org.example.util.DateTimeUtil;
import org.example.util.ValidateStatus;

public record TicketingAlertReservationApiRequest(

    @ArraySchema(schema = @Schema(description = "공연 티켓팅 알림 시간", example = "2999-01-01T23:59"))
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    List<String> alertTimes
) {

    public TicketingAlertReservationServiceRequest toServiceRequest(
        UUID userId,
        UUID showId,
        TicketingApiType type
    ) {
        return TicketingAlertReservationServiceRequest.builder()
            .userId(userId)
            .showId(showId)
            .type(type)
            .alertAts(ValidateStatus.checkNullOrEmpty(alertTimes).stream()
                .map(DateTimeUtil::parseDateTime)
                .toList())
            .build();
    }
}
