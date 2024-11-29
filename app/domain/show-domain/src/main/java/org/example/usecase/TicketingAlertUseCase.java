package org.example.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.dto.usershow.request.TicketingAlertReservationDomainRequest;
import org.example.dto.usershow.response.TicketingAlertsDomainResponse;
import org.example.entity.BaseEntity;
import org.example.entity.usershow.TicketingAlert;
import org.example.repository.ticketing.TicketingAlertRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TicketingAlertUseCase {

    private final TicketingAlertRepository ticketingAlertRepository;

    public List<TicketingAlert> findTicketingAlerts(
        UUID userId,
        UUID showId
    ) {
        return ticketingAlertRepository.findAllByUserIdAndShowIdAndIsDeletedFalse(userId, showId);
    }

    public List<TicketingAlert> findTicketingAlertsByUserId(UUID userId) {
        return ticketingAlertRepository.findAllByUserIdAndIsDeletedFalse(userId);
    }

    public long countAlertShows(UUID userId, LocalDateTime now) {
        return ticketingAlertRepository.countValidTicketingAlerts(userId, now);
    }

    @Transactional
    public TicketingAlertsDomainResponse alertReservation(
        TicketingAlertReservationDomainRequest request
    ) {
        List<TicketingAlert> existingTicketingAlerts = ticketingAlertRepository.findAllByUserIdAndShowIdAndIsDeletedFalse(
            request.userId(),
            request.showId()
        );

        List<LocalDateTime> existingAlertAts = existingTicketingAlerts.stream()
            .map(TicketingAlert::getAlertTime)
            .filter(alertTime -> request.alertAts().contains(alertTime))
            .toList();

        return TicketingAlertsDomainResponse.builder()
            .name(request.name())
            .showId(request.showId())
            .ticketingAt(request.ticketingAt())
            .addAts(addAlerts(request, existingAlertAts))
            .deleteAts(deleteAlerts(request, existingTicketingAlerts))
            .build();
    }

    public void deleteAllByUserId(UUID userId) {
        ticketingAlertRepository.deleteAllByUserId(userId);
    }

    private List<LocalDateTime> addAlerts(
        TicketingAlertReservationDomainRequest request,
        List<LocalDateTime> existingAlertTimes
    ) {
        List<TicketingAlert> alertsToAdd = request.alertAts().stream()
            .filter(alertTime -> !existingAlertTimes.contains(alertTime))
            .map(alertTime -> TicketingAlert.builder()
                .name(request.name())
                .alertTime(alertTime)
                .userId(request.userId())
                .showId(request.showId())
                .build()
            )
            .toList();
        ticketingAlertRepository.saveAll(alertsToAdd);

        return alertsToAdd.stream().map(TicketingAlert::getAlertTime).toList();
    }

    private List<LocalDateTime> deleteAlerts(
        TicketingAlertReservationDomainRequest request,
        List<TicketingAlert> existingAlerts
    ) {
        List<TicketingAlert> alertsToRemove = existingAlerts.stream()
            .filter(alert -> !request.alertAts().contains(alert.getAlertTime()))
            .toList();
        alertsToRemove.forEach(BaseEntity::softDelete);

        return alertsToRemove.stream().map(TicketingAlert::getAlertTime).toList();
    }
}
