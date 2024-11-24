package show.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ViewCountConcurrencyTest {

    @ParameterizedTest(name = "{index} {displayName} executeCount={0}")
    @ValueSource(ints = {50, 100, 200, 300})
    @DisplayName("전역 ReentrantLock을 사용해 viewCount 증가 시 동시성 문제가 발생하지 않는다.")
    void viewCountIncrementWithGlobalReentrantLock(int executeCount) throws InterruptedException {
        //given
        ViewCount viewCount = new ViewCount();
        CountDownLatch doneSignal = new CountDownLatch(executeCount);
        ExecutorService executorService = Executors.newFixedThreadPool(executeCount);

        //when
        for (int i = 0; i < executeCount; i++) {
            executorService.execute(() -> {
                viewCount.incrementWithGlobalLock();
                doneSignal.countDown();
            });
        }
        doneSignal.await();
        executorService.shutdown();

        //then
        assertThat(viewCount.getCount()).isEqualTo(executeCount);
    }

    @ParameterizedTest(name = "{index} {displayName} executeCount={0}")
    @ValueSource(ints = {50, 100, 200, 300})
    @DisplayName("개별 ReentrantLock을 사용해 viewCount 증가 시 동시성 문제가 발생하지 않는다.")
    void viewCountIncrementWithIndividualReentrantLock(int executeCount) throws InterruptedException {
        //given
        ViewCount viewCount = new ViewCount();
        CountDownLatch doneSignal = new CountDownLatch(executeCount);
        ExecutorService executorService = Executors.newFixedThreadPool(executeCount);

        //when
        for (int i = 0; i < executeCount; i++) {
            final int id = i;
            executorService.execute(() -> {
                viewCount.incrementWithIndividualLock((id % 5L));
                doneSignal.countDown();
            });
        }
        doneSignal.await();
        executorService.shutdown();

        //then
        assertThat(viewCount.getCount()).isEqualTo(executeCount);
    }

    @ParameterizedTest(name = "{index} {displayName} executeCount={0}")
    @ValueSource(ints = {50, 100, 200, 300})
    @DisplayName("viewCount 증가 시 동시성 문제가 발생한다.")
    void viewCountIncrementWithNoLock(int executeCount) throws InterruptedException {
        //given
        ViewCount viewCount = new ViewCount();
        CountDownLatch doneSignal = new CountDownLatch(executeCount);
        ExecutorService executorService = Executors.newFixedThreadPool(executeCount);

        //when
        for (int i = 0; i < executeCount; i++) {
            executorService.execute(() -> {
                try {
                    viewCount.increment();
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
                doneSignal.countDown();
            });
        }
        doneSignal.await();
        executorService.shutdown();

        //then
        assertThat(viewCount.getCount()).isLessThanOrEqualTo(executeCount);
    }
}
