package com.example.genre.controller;

import com.example.genre.controller.dto.param.GenrePaginationApiParam;
import com.example.genre.controller.dto.param.GenreSubscriptionPaginationApiParam;
import com.example.genre.controller.dto.param.GenreUnsubscriptionPaginationApiParam;
import com.example.genre.controller.dto.request.GenrePaginationApiRequest;
import com.example.genre.controller.dto.request.GenreSubscriptionApiRequest;
import com.example.genre.controller.dto.request.GenreSubscriptionPaginationApiRequest;
import com.example.genre.controller.dto.request.GenreUnsubscriptionApiRequest;
import com.example.genre.controller.dto.request.GenreUnsubscriptionPaginationApiRequest;
import com.example.genre.controller.dto.response.GenreSubscriptionApiResponse;
import com.example.genre.controller.dto.response.GenreUnsubscriptionApiResponse;
import com.example.genre.controller.dto.response.NumberOfSubscribedGenreApiResponse;
import com.example.genre.service.GenreService;
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
@RequestMapping("/api/v1/genres")
@Tag(name = "장르")
public class GenreController {

    private final GenreService genreService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "장르 전체 목록 조회")
    public SuccessResponse<PaginationApiResponse<GenrePaginationApiParam>> getGenres(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @ParameterObject GenrePaginationApiRequest request
    ) {
        UUID userId = ValidatorUser.getUserId(info);
        var response = genreService.findGenres(request.toServiceRequest(userId));
        var data = response.data().stream()
            .map(GenrePaginationApiParam::new)
            .toList();

        CursorApiResponse cursor = Optional.ofNullable(CursorApiResponse.getLastElement(data))
            .map(element -> CursorApiResponse.toCursorId(element.id()))
            .orElse(CursorApiResponse.noneCursor());

        return SuccessResponse.ok(
            PaginationApiResponse.<GenrePaginationApiParam>builder()
                .hasNext(response.hasNext())
                .data(data)
                .cursor(cursor)
                .build()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/unsubscriptions")
    @Operation(summary = "구독하지 않은 장르 목록 조회")
    public SuccessResponse<PaginationApiResponse<GenreUnsubscriptionPaginationApiParam>> getUnsubscribedGenres(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @ParameterObject GenreUnsubscriptionPaginationApiRequest request
    ) {
        var response = genreService.findGenreUnSubscriptions(
            request.toServiceRequest(info.userId())
        );
        var data = response.data().stream()
            .map(GenreUnsubscriptionPaginationApiParam::new)
            .toList();

        CursorApiResponse cursor = Optional.ofNullable(CursorApiResponse.getLastElement(data))
            .map(element -> CursorApiResponse.toCursorId(element.id()))
            .orElse(CursorApiResponse.noneCursor());

        return SuccessResponse.ok(
            PaginationApiResponse.<GenreUnsubscriptionPaginationApiParam>builder()
                .hasNext(response.hasNext())
                .data(data)
                .cursor(cursor)
                .build()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/subscriptions")
    @Operation(summary = "구독한 장르 목록 조회")
    public SuccessResponse<PaginationApiResponse<GenreSubscriptionPaginationApiParam>> getSubscribedGenres(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @ParameterObject GenreSubscriptionPaginationApiRequest request
    ) {
        var response = genreService.findGenreSubscriptions(
            request.toServiceRequest(info.userId())
        );
        var data = response.data().stream()
            .map(GenreSubscriptionPaginationApiParam::new)
            .toList();

        CursorApiResponse cursor = Optional.ofNullable(CursorApiResponse.getLastElement(data))
            .map(element -> CursorApiResponse.toCursorId(element.id()))
            .orElse(CursorApiResponse.noneCursor());

        return SuccessResponse.ok(
            PaginationApiResponse.<GenreSubscriptionPaginationApiParam>builder()
                .hasNext(response.hasNext())
                .data(data)
                .cursor(cursor)
                .build()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/subscriptions/count")
    @Operation(summary = "구독한 장르 수")
    public SuccessResponse<NumberOfSubscribedGenreApiResponse> getNumberOfSubscribedGenre(
        @AuthenticationPrincipal AuthenticatedInfo info
    ) {
        return SuccessResponse.ok(
            NumberOfSubscribedGenreApiResponse.from(
                genreService.countSubscribedGenres(info.userId())
            )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/subscribe")
    @Operation(summary = "구독하기")
    public SuccessResponse<GenreSubscriptionApiResponse> subscribe(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @RequestBody GenreSubscriptionApiRequest request
    ) {
        return SuccessResponse.ok(
            GenreSubscriptionApiResponse.from(
                genreService.subscribe(request.toServiceRequest(info.userId()))
            )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/unsubscribe")
    @Operation(summary = "구독 취소하기")
    public SuccessResponse<GenreUnsubscriptionApiResponse> unsubscribe(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @Valid @RequestBody GenreUnsubscriptionApiRequest request
    ) {
        return SuccessResponse.ok(
            GenreUnsubscriptionApiResponse.from(
                genreService.unsubscribe(request.toServiceRequest(info.userId()))
            )
        );
    }
}
