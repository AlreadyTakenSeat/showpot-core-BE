package org.example.client;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.metric.InternalApiMonitored;
import org.example.property.AlarmServerProperty;
import org.example.service.dto.response.NotificationExistServiceResponse;
import org.example.service.dto.response.NotificationPaginationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlarmClientManager {

    private final AlarmServerProperty alarmServerProperty;

    @InternalApiMonitored(name = "status")
    public NotificationExistServiceResponse getNotificationExist(
        String userFcmToken
    ) {
        log.info("{}/show-alarm/checked?fcmToken={}", alarmServerProperty.apiURL(), userFcmToken);

        ResponseEntity<NotificationExistServiceResponse> result =
            RestClient.create(alarmServerProperty.apiURL() + "/show-alarm/checked")
                .get()
                .uri("?fcmToken=" + userFcmToken)
                .retrieve()
                .toEntity(NotificationExistServiceResponse.class);

        handleApiError(result, "getNotificationExist");
        return result.getBody();
    }

    @InternalApiMonitored(name = "list")
    public NotificationPaginationResponse getNotificationPagination(String userFcmToken,
        UUID cursorId, LocalDateTime cursorValue, int size) {
        ResponseEntity<NotificationPaginationResponse> result =
            RestClient.create(createNotificationsUrl(userFcmToken, cursorId, cursorValue, size))
                .post()
                .retrieve()
                .toEntity(NotificationPaginationResponse.class);

        handleApiError(result, "findNotifications");
        return result.getBody();
    }

    private String createNotificationsUrl(String userFcmToken, UUID cursorId, LocalDateTime cursorValue, int size) {
        StringBuilder urlBuilder = new StringBuilder(alarmServerProperty.apiURL())
            .append("/show-alarm?fcmToken=")
            .append(userFcmToken)
            .append("&size=")
            .append(size);

        if (cursorId != null) {
            urlBuilder.append("&cursorId=").append(cursorId);
        }

        if (cursorValue != null) {
            urlBuilder.append("&cursorValue=").append(cursorValue);
        }

        return urlBuilder.toString();
    }

    private void handleApiError(ResponseEntity<?> response, String methodName) {
        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error("Alarm Server API failed in {}: {}", methodName, response);
            throw new RuntimeException("Alarm Server API failed");
        }
    }

}
