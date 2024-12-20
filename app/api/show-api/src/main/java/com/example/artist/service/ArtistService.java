package com.example.artist.service;

import com.example.artist.service.dto.param.ArtistSearchPaginationServiceParam;
import com.example.artist.service.dto.param.ArtistSubscriptionPaginationServiceParam;
import com.example.artist.service.dto.param.ArtistUnsubscriptionPaginationServiceParam;
import com.example.artist.service.dto.request.ArtistSearchPaginationServiceRequest;
import com.example.artist.service.dto.request.ArtistSubscriptionPaginationServiceRequest;
import com.example.artist.service.dto.request.ArtistSubscriptionServiceRequest;
import com.example.artist.service.dto.request.ArtistUnsubscriptionPaginationServiceRequest;
import com.example.artist.service.dto.request.ArtistUnsubscriptionServiceRequest;
import com.example.artist.service.dto.response.ArtistIdsServiceResponse;
import com.example.artist.service.dto.response.ArtistSubscriptionServiceResponse;
import com.example.artist.service.dto.response.ArtistUnsubscriptionServiceResponse;
import com.example.artist.service.dto.response.NumberOfSubscribedArtistServiceResponse;
import com.example.pub.MessagePublisher;
import com.example.pub.message.ArtistServiceMessage;
import com.example.pub.message.ArtistSubscriptionServiceMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.response.CursorApiResponse;
import org.example.dto.response.PaginationServiceResponse;
import org.example.entity.artist.Artist;
import org.example.entity.usershow.ArtistSubscription;
import org.example.usecase.ArtistSubscriptionUseCase;
import org.example.usecase.ArtistUseCase;
import org.example.usecase.UserUseCase;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistService {


    private final ArtistUseCase artistUseCase;
    private final ArtistSubscriptionUseCase artistSubscriptionUseCase;
    private final UserUseCase userUseCase;
    private final MessagePublisher messagePublisher;
    private final ReentrantLock lock = new ReentrantLock();

    public PaginationServiceResponse<ArtistSearchPaginationServiceParam> searchArtist(
        ArtistSearchPaginationServiceRequest request
    ) {
        var response = artistUseCase.searchArtist(request.toDomainRequest());

        List<UUID> subscribedArtistIds = request.userId() == null
            ? List.of()
            : artistSubscriptionUseCase.findArtistSubscriptionByUserId(request.userId())
                .stream()
                .map(ArtistSubscription::getArtistId)
                .toList();

        List<ArtistSearchPaginationServiceParam> data = response.data().stream()
            .map(artistResponse -> ArtistSearchPaginationServiceParam.from(
                    artistResponse,
                    subscribedArtistIds
                )
            )
            .toList();

        return PaginationServiceResponse.of(data, response.hasNext(),
            CursorApiResponse.toCursorId(response.offset())
        );
    }

    /**
     * 사용자가 아티스트를 구독하도록 처리하는 메서드입니다.
     * 요청한 아티스트가 DB에 존재하지 않을 경우 새로 생성하며,
     * 구독이 완료되면 알람 서버로 Pub을 합니다.
     *
     * @param request 사용자의 ID와 구독하려는 Spotify 아티스트 ID 목록을 포함하는 요청 객체
     * @return 구독에 성공한 아티스트 ID 목록을 담은 응답 객체
     */
    public ArtistSubscriptionServiceResponse subscribe(ArtistSubscriptionServiceRequest request) {
        List<Artist> requestArtist = findOrCreateArtistByLock(request);
        List<UUID> requestArtistIds = requestArtist.stream()
            .map(Artist::getId)
            .toList();

        var userFcmToken = userUseCase.findUserFcmTokensByUserId(request.userId());

        var subscribedArtistIds = artistSubscriptionUseCase
            .subscribe(requestArtistIds, request.userId()).stream()
            .map(ArtistSubscription::getArtistId)
            .toList();

        var subscribedArtistMessage = requestArtist
            .stream()
            .filter(artist -> subscribedArtistIds.contains(artist.getId()))
            .map(ArtistServiceMessage::from)
            .toList();

        messagePublisher.publishArtistSubscription(
            "artistSubscription",
            ArtistSubscriptionServiceMessage.from(
                userFcmToken,
                subscribedArtistMessage
            )
        );

        return ArtistSubscriptionServiceResponse.builder()
            .subscriptionArtistIds(
                requestArtist.stream()
                    .filter(artist -> subscribedArtistIds.contains(artist.getId()))
                    .map(ArtistIdsServiceResponse::from)
                    .toList()
            ).build();
    }

    public List<Artist> findOrCreateArtistByLock(ArtistSubscriptionServiceRequest request) {
        List<Artist> artists = new ArrayList<>();
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(1500L, TimeUnit.MILLISECONDS);

            if (!isLocked) {
                log.error("Artist subscribe event: {}", request);
                return List.of();
            }
            artists = artistUseCase.findOrCreateArtistBySpotifyId(request.spotifyArtistIds());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted while trying to lock for findOrCreate Artist: {}", request);
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }

        return artists;
    }

    public ArtistUnsubscriptionServiceResponse unsubscribe(
        ArtistUnsubscriptionServiceRequest request
    ) {
        var unsubscribedArtists = artistSubscriptionUseCase.unsubscribe(
            request.artistIds(),
            request.userId()
        );
        var unsubscribedArtistMessage = unsubscribedArtists.stream()
            .map(ArtistServiceMessage::toUnsubscribe)
            .toList();

        var userFcmToken = userUseCase.findUserFcmTokensByUserId(request.userId());

        messagePublisher.publishArtistSubscription(
            "artistUnsubscription",
            ArtistSubscriptionServiceMessage.from(
                userFcmToken,
                unsubscribedArtistMessage
            )
        );

        return ArtistUnsubscriptionServiceResponse.builder()
            .successUnsubscriptionArtistIds(
                unsubscribedArtists.stream()
                    .map(ArtistSubscription::getArtistId)
                    .toList()
            ).build();
    }

    public PaginationServiceResponse<ArtistSubscriptionPaginationServiceParam> findArtistSubscriptions(
        ArtistSubscriptionPaginationServiceRequest request
    ) {
        List<ArtistSubscription> subscriptions = artistSubscriptionUseCase.findSubscriptionList(
            request.userId()
        );
        List<UUID> subscriptionArtistIds = subscriptions.stream()
            .map(ArtistSubscription::getArtistId)
            .toList();

        if (subscriptionArtistIds.isEmpty()) {
            return PaginationServiceResponse.of(List.of(), false);
        }

        var response = artistUseCase.findAllArtistInCursorPagination(
            request.toDomainRequest(subscriptionArtistIds));
        List<ArtistSubscriptionPaginationServiceParam> data = response.data().stream()
            .map(ArtistSubscriptionPaginationServiceParam::new)
            .toList();

        return PaginationServiceResponse.of(data, response.hasNext());
    }

    public PaginationServiceResponse<ArtistUnsubscriptionPaginationServiceParam> findArtistUnsubscriptions(
        ArtistUnsubscriptionPaginationServiceRequest request
    ) {
        List<UUID> subscriptionArtistIds = request.userId() == null
            ? List.of()
            : getSubscriptionArtistIds(request.userId());

        var response = artistUseCase.findAllArtistInCursorPagination(
            request.toDomainRequest(subscriptionArtistIds));
        List<ArtistUnsubscriptionPaginationServiceParam> data = response.data().stream()
            .map(ArtistUnsubscriptionPaginationServiceParam::new)
            .toList();

        return PaginationServiceResponse.of(data, response.hasNext());
    }

    public NumberOfSubscribedArtistServiceResponse countSubscribedArtists(UUID userId) {
        return NumberOfSubscribedArtistServiceResponse.from(
            artistSubscriptionUseCase.countSubscribedArtists(userId)
        );
    }

    private List<UUID> getSubscriptionArtistIds(UUID userId) {
        List<ArtistSubscription> subscriptions = artistSubscriptionUseCase.findSubscriptionList(
            userId);

        return subscriptions.stream()
            .map(ArtistSubscription::getArtistId)
            .toList();
    }
}
