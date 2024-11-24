package org.example.usecase;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.show.request.ShowAlertPaginationDomainRequest;
import org.example.dto.show.request.ShowPaginationDomainRequest;
import org.example.dto.show.request.ShowSearchPaginationDomainRequest;
import org.example.dto.show.response.ShowAlertPaginationDomainResponse;
import org.example.dto.show.response.ShowDetailDomainResponse;
import org.example.dto.show.response.ShowSearchPaginationDomainResponse;
import org.example.dto.show.response.ShowTicketingPaginationDomainResponse;
import org.example.dto.viewcount.ShowViewCountEvent;
import org.example.entity.show.Show;
import org.example.entity.show.ShowTicketingTime;
import org.example.repository.show.ShowRepository;
import org.example.repository.show.showsearch.ShowSearchRepository;
import org.example.repository.show.showticketing.ShowTicketingTimeRepository;
import org.example.vo.TicketingType;
import org.springframework.context.event.EventListener;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowUseCase {

    private final ShowRepository showRepository;
    private final ShowSearchRepository showSearchRepository;
    private final ShowTicketingTimeRepository showTicketingTimeRepository;
    private final Map<UUID, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    public ShowDetailDomainResponse findShowDetail(UUID id) {
        return showRepository.findShowDetailById(id).orElseThrow(NoSuchElementException::new);
    }

    public ShowTicketingPaginationDomainResponse findShows(ShowPaginationDomainRequest request) {
        return showRepository.findShows(request);
    }

    public List<Show> findShowsInIds(List<UUID> showIds) {
        return showRepository.findShowsByIdInAndIsDeletedFalse(showIds);
    }

    @Async
    @EventListener
    @Retryable(
            retryFor = OptimisticLockingFailureException.class,
            maxAttempts = 10,
            backoff = @Backoff(delay = 100, multiplier = 1.3, random = true),
            recover = "recoverView"
    )
    @Transactional
    public void view(ShowViewCountEvent showViewCountEvent) {
        UUID id = showViewCountEvent.showId();
        ReentrantLock individualLock = lockMap.computeIfAbsent(id, k -> new ReentrantLock());

        boolean isLocked = false;
        try {
            isLocked = individualLock.tryLock(1500L, TimeUnit.MILLISECONDS);

            if (!isLocked) {
                log.error("Failed to lock for show ID: {}", id);
                return;
            }
            findShowWithLockOrThrowNoSuchElementException(id).view();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted while trying to lock for show ID: {}", id);
        } finally {
            if (isLocked) {
                individualLock.unlock();
                lockMap.compute(id, (key, value) -> value != null && !value.isLocked() ? null : value);
            }
        }
    }

    @Recover
    public void recoverView(OptimisticLockingFailureException e, ShowViewCountEvent showViewCountEvent) {
        log.error("Failed to increment view count for show ID: {}", showViewCountEvent.showId());
    }

    private Show findShowWithLockOrThrowNoSuchElementException(UUID id) {
        return showRepository.findByIdAndIsDeletedWithLock(id).orElseThrow(NoSuchElementException::new);
    }

    public ShowSearchPaginationDomainResponse searchShow(
        ShowSearchPaginationDomainRequest request
    ) {
        return showSearchRepository.searchShow(request);
    }

    public ShowAlertPaginationDomainResponse findAlertShows(
        ShowAlertPaginationDomainRequest request
    ) {
        return showTicketingTimeRepository.findShowAlerts(request);
    }

    public ShowTicketingTime findTicketingAlertReservation(
        UUID showId,
        TicketingType type
    ) {
        return showTicketingTimeRepository.findByShowIdAndTicketingTypeAndIsDeletedFalse(showId, type)
            .orElseThrow(NoSuchElementException::new);
    }

    public ShowTicketingTime findTicketingTimeWithShow(
        UUID showId,
        TicketingType type
    ) {
        return showTicketingTimeRepository.findByShowIdAndTicketingTypeWithShow(showId, type)
            .orElseThrow(NoSuchElementException::new);
    }

    public long findTerminatedTicketingShowsCount(List<UUID> showIds, LocalDateTime now) {
        return showRepository.findTerminatedTicketingShowsCount(showIds, now);
    }

    public Show findShowOrThrowNoSuchElementException(UUID id) {
        return showRepository.findByIdAndIsDeletedFalse(id).orElseThrow(NoSuchElementException::new);
    }
}
