package org.example.user.controller.dto.response;

import java.util.List;
import lombok.Builder;
import org.example.user.controller.dto.param.SimpleNotificationApiParam;

@Builder
public record NotificationsApiResponse(
    List<SimpleNotificationApiParam> notifications
) {

}
