package com.example.show.controller.dto.request;

import com.example.show.controller.vo.TicketingApiType;
import com.example.show.service.dto.request.TicketingAlertReservationServiceRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import org.example.util.DateTimeUtil;
import org.example.util.ValidateStatus;

public record TicketingAlertReservationApiRequest(

    @NotEmpty
    @NotNull
    @ArraySchema(schema = @Schema(description = "공연 티켓팅 알림 시간", example = "2999-1-1 23:59"))
    @JsonFormat(pattern = "yyyy-M-dd HH:mm")
    List<@NotBlank String> alertTimes
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
