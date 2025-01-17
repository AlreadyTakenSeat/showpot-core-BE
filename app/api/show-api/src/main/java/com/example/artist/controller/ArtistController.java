package com.example.artist.controller;

import com.example.artist.controller.dto.param.ArtistSearchPaginationApiParam;
import com.example.artist.controller.dto.param.ArtistSubscriptionPaginationApiParam;
import com.example.artist.controller.dto.param.ArtistUnsubscriptionPaginationApiParam;
import com.example.artist.controller.dto.request.ArtistSearchPaginationApiRequest;
import com.example.artist.controller.dto.request.ArtistSubscriptionApiRequest;
import com.example.artist.controller.dto.request.ArtistSubscriptionPaginationApiRequest;
import com.example.artist.controller.dto.request.ArtistUnsubscriptionApiRequest;
import com.example.artist.controller.dto.request.ArtistUnsubscriptionPaginationApiRequest;
import com.example.artist.controller.dto.response.ArtistSubscriptionApiResponse;
import com.example.artist.controller.dto.response.ArtistUnsubscriptionApiResponse;
import com.example.artist.controller.dto.response.NumberOfSubscribedArtistApiResponse;
import com.example.artist.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.dto.response.CursorApiResponse;
import org.example.dto.response.PaginationApiResponse;
import org.example.dto.response.SuccessResponse;
import org.example.security.dto.AuthenticatedInfo;
import org.example.util.ValidatorUser;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/artists")
@Tag(name = "아티스트")
public class ArtistController {

    private final ArtistService artistService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/unsubscriptions")
    @Operation(summary = "구독하지 않은 아티스트 목록 조회")
    public SuccessResponse<PaginationApiResponse<ArtistUnsubscriptionPaginationApiParam>> getUnsubscribedArtists(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @ParameterObject ArtistUnsubscriptionPaginationApiRequest request
    ) {
        UUID userId = ValidatorUser.getUserId(info);
        var response = artistService.findArtistUnsubscriptions(
            request.toServiceRequest(userId)
        );
        var data = response.data().stream()
            .map(ArtistUnsubscriptionPaginationApiParam::from)
            .toList();

        CursorApiResponse cursor = Optional.ofNullable(CursorApiResponse.getLastElement(data))
            .map(element -> CursorApiResponse.toCursorId(element.id()))
            .orElse(CursorApiResponse.noneCursor());

        return SuccessResponse.ok(
            PaginationApiResponse.<ArtistUnsubscriptionPaginationApiParam>builder()
                .hasNext(response.hasNext())
                .data(data)
                .cursor(cursor)
                .build()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/subscriptions")
    @Operation(summary = "구독한 아티스트 목록 조회")
    public SuccessResponse<PaginationApiResponse<ArtistSubscriptionPaginationApiParam>> getSubscribedArtists(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @ParameterObject ArtistSubscriptionPaginationApiRequest request
    ) {
        var response = artistService.findArtistSubscriptions(
            request.toServiceRequest(info.userId())
        );
        var data = response.data().stream()
            .map(ArtistSubscriptionPaginationApiParam::from)
            .toList();

        CursorApiResponse cursor = Optional.ofNullable(CursorApiResponse.getLastElement(data))
            .map(element -> CursorApiResponse.toCursorId(element.id()))
            .orElse(CursorApiResponse.noneCursor());

        return SuccessResponse.ok(
            PaginationApiResponse.<ArtistSubscriptionPaginationApiParam>builder()
                .hasNext(response.hasNext())
                .data(data)
                .cursor(cursor)
                .build()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/subscriptions/count")
    @Operation(summary = "구독한 아티스트 수")
    public SuccessResponse<NumberOfSubscribedArtistApiResponse> getNumberOfSubscribedArtist(
        @AuthenticationPrincipal AuthenticatedInfo info
    ) {
        return SuccessResponse.ok(
            NumberOfSubscribedArtistApiResponse.from(
                artistService.countSubscribedArtists(info.userId())
            )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/subscribe")
    @Operation(summary = "구독하기")
    public SuccessResponse<ArtistSubscriptionApiResponse> subscribe(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @RequestBody ArtistSubscriptionApiRequest request
    ) {
        return SuccessResponse.ok(
            ArtistSubscriptionApiResponse.from(
                artistService.subscribe(request.toServiceRequest(info.userId()))
            )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/unsubscribe")
    @Operation(summary = "구독 취소하기")
    public SuccessResponse<ArtistUnsubscriptionApiResponse> unsubscribe(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @RequestBody ArtistUnsubscriptionApiRequest request
    ) {
        return SuccessResponse.ok(
            ArtistUnsubscriptionApiResponse.from(
                artistService.unsubscribe(request.toServiceRequest(info.userId()))
            )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    @Operation(summary = "검색하기")
    public SuccessResponse<PaginationApiResponse<ArtistSearchPaginationApiParam>> search(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @ParameterObject ArtistSearchPaginationApiRequest request
    ) {
        UUID userId = ValidatorUser.getUserId(info);
        var response = artistService.searchArtist(request.toServiceRequest(userId));
        var data = response.data().stream()
            .map(ArtistSearchPaginationApiParam::from)
            .toList();

        return SuccessResponse.ok(
            PaginationApiResponse.<ArtistSearchPaginationApiParam>builder()
                .hasNext(response.hasNext())
                .data(data)
                .cursor(response.cursor())
                .build()
        );
    }
}
