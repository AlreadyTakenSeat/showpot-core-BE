package show.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.artist.service.ArtistService;
import com.example.pub.MessagePublisher;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.example.usecase.ArtistSubscriptionUseCase;
import org.example.usecase.ArtistUseCase;
import org.example.usecase.UserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import show.fixture.dto.ArtistRequestDtoFixture;

class ArtistServiceTest {

    private final ArtistUseCase artistUseCase = mock(ArtistUseCase.class);
    private final ArtistSubscriptionUseCase artistSubscriptionUseCase = mock(
        ArtistSubscriptionUseCase.class);
    private final UserUseCase userUseCase = mock(UserUseCase.class);
    private final MessagePublisher messagePublisher = mock(MessagePublisher.class);

    private final ArtistService artistService = new ArtistService(
        artistUseCase,
        artistSubscriptionUseCase,
        userUseCase, messagePublisher);

    @Test
    @DisplayName("전역 ReentrantLock을 사용해 아티스트 조회 혹은 생성 시 동시성 문제가 발생하지 않는다.")
    void testFindOrCreateArtistByLock_Concurrency() throws InterruptedException {
        //given
        int executeCount = 10;
        CountDownLatch doneSignal = new CountDownLatch(executeCount);
        ExecutorService executorService = Executors.newFixedThreadPool(executeCount);

        //when
        for (int i = 0; i < executeCount; i++) {
            executorService.execute(() -> {
                artistService.findOrCreateArtistByLock(
                    ArtistRequestDtoFixture.artistSubscriptionServiceRequest(5));
                doneSignal.countDown();
            });
        }

        doneSignal.await();
        executorService.shutdown();

        //then
        verify(artistUseCase, times(executeCount)).findOrCreateArtistBySpotifyId(anyList());
        assertThat(doneSignal.getCount()).isZero();
    }
}
