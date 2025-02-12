package org.example.user.service.dto.response;

import lombok.Builder;

@Builder
public record NotificationExistServiceResponse(
    boolean isActivate
) {

}
