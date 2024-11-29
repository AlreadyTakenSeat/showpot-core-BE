package org.example.consumer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.viewcount.ShowViewCountEvent;
import org.example.usecase.ShowUseCase;
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
public class ShowViewCountConsumer {

    private final ShowUseCase showUseCase;
    private final Map<UUID, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    @Retryable(
        retryFor = OptimisticLockingFailureException.class,
        maxAttempts = 10,
        backoff = @Backoff(delay = 100, multiplier = 1.3, random = true),
        recover = "recoverView"
    )
    @Async
    @EventListener
    public void consumeShowViewCountEvent(ShowViewCountEvent showViewCountEvent) {
        UUID showId = showViewCountEvent.showId();
        ReentrantLock individualLock = lockMap.computeIfAbsent(showId, k -> new ReentrantLock());

        boolean isLocked = false;
        try {
            isLocked = individualLock.tryLock(1500L, TimeUnit.MILLISECONDS);

            if (!isLocked) {
                log.error("Failed to lock for show ID: {}", showId);
                return;
            }
            showUseCase.view(showId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted while trying to lock for show ID: {}", showId);
        } finally {
            if (isLocked) {
                individualLock.unlock();
                lockMap.compute(showId,
                    (key, value) -> value != null && !value.isLocked() ? null : value);
            }
        }
    }

    @Recover
    public void recoverView(OptimisticLockingFailureException e,
        ShowViewCountEvent showViewCountEvent) {
        log.error("Failed to increment view count for show ID: {}", showViewCountEvent.showId());
    }
}
