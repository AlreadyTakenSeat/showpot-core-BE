package org.example.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.AlarmClientManager;
import org.example.entity.show.Show;
import org.example.repository.show.ShowRepository;
import org.example.repository.user.UserRepository;
import org.example.service.dto.response.NotificationExistServiceResponse;
import org.example.service.dto.response.NotificationPaginationResponse.NotificationInfoResponse;
import org.example.service.dto.response.NotificationServiceResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAlarmService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final AlarmClientManager alarmClientManager;

    public NotificationExistServiceResponse getNotificationExist(UUID userId) {
        String userFcmToken = findUserFcmTokenById(userId);

        return alarmClientManager.getNotificationExist(userFcmToken);
    }

    public NotificationServiceResponse findNotifications(UUID userId, UUID cursorId, int size) {
        String userFcmToken = findUserFcmTokenById(userId);

        var response = alarmClientManager.getNotificationPagination(cursorId, size, userFcmToken);
        if (response != null) {
            List<UUID> showIds = response.data().stream()
                .map(NotificationInfoResponse::showId)
                .toList();
            List<Show> shows = showRepository.findShowsByIdInAndIsDeletedFalse(showIds);

            return NotificationServiceResponse.of(response, shows);
        }

        return NotificationServiceResponse.noneData();
    }

    private String findUserFcmTokenById(UUID userId) {
        return userRepository.findUserFcmTokensByUserId(userId)
            .orElseThrow(NoSuchElementException::new);
    }
}
