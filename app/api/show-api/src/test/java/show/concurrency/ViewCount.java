package show.concurrency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ViewCount {

    private final Map<Long, ReentrantLock> lockMap = new ConcurrentHashMap<>();
    private final Lock globalLock = new ReentrantLock();
    private final AtomicInteger count = new AtomicInteger(0); // int로 사용할 경우 동시성 문제가 발생, AtomicInteger의 경우 낙관적 락

    public void incrementWithIndividualLock(Long id) {
        ReentrantLock individualLock = lockMap.computeIfAbsent(id, k -> new ReentrantLock());

        boolean isLocked = false;
        try {
            isLocked = individualLock.tryLock(1, TimeUnit.SECONDS);

            if (!isLocked) {
                System.out.println("Failed to lock for show ID: " + id);
                return;
            }
            increment();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while trying to lock for show ID: " + id);
        } finally {
            if (isLocked) {
                individualLock.unlock();
                lockMap.compute(id, (key, value) -> value != null && !value.isLocked() ? null : value);
            }
        }
    }

    public void incrementWithGlobalLock() {
        boolean isLocked = false;
        try {
            isLocked = globalLock.tryLock(5, TimeUnit.SECONDS);

            if (!isLocked) {
                System.out.println("Failed to lock for show");
                return;
            }
            increment();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while trying to lock for show");
        } finally {
            if (isLocked) {
                globalLock.unlock();
            }
        }
    }

    public void increment() throws InterruptedException {
        Thread.sleep(5);
        count.incrementAndGet();
        Thread.sleep(5);
    }

    public int getCount() {
        return count.get();
    }
}
