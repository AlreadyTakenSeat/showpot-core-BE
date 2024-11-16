package com.example.show.controller;

import com.example.show.controller.dto.param.ShowPaginationApiParam;
import com.example.show.controller.dto.param.ShowSearchPaginationApiParam;
import com.example.show.controller.dto.request.ShowPaginationApiRequest;
import com.example.show.controller.dto.request.ShowSearchPaginationApiRequest;
import com.example.show.controller.dto.response.ShowDetailApiResponse;
import com.example.show.service.ShowService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shows")
@Tag(name = "공연")
public class ShowController {

    private final ShowService showService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "공연 목록 조회")
    public SuccessResponse<PaginationApiResponse<ShowPaginationApiParam>> getShows(
        @Valid @ParameterObject ShowPaginationApiRequest request
    ) {
        var response = showService.findShows(request.toServiceRequest());
        var data = response.data().stream()
            .map(ShowPaginationApiParam::from)
            .toList();

        CursorApiResponse cursor = Optional.ofNullable(CursorApiResponse.getLastElement(data))
            .map(element -> CursorApiResponse.toCursorId(element.id()))
            .orElse(CursorApiResponse.noneCursor());

        return SuccessResponse.ok(
            PaginationApiResponse.<ShowPaginationApiParam>builder()
                .data(data)
                .hasNext(response.hasNext())
                .cursor(cursor)
                .build()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{showId}")
    @Operation(summary = "공연 상세 조회")
    public SuccessResponse<ShowDetailApiResponse> getShow(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @PathVariable("showId") UUID showId,
        @RequestHeader(value = "Device-Token") String deviceToken
    ) {
        UUID userId = ValidatorUser.getUserId(info);

        return SuccessResponse.ok(
            ShowDetailApiResponse.from(showService.getShow(userId, showId, deviceToken))
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    @Operation(summary = "검색하기")
    public SuccessResponse<PaginationApiResponse<ShowSearchPaginationApiParam>> search(
        @Valid @ParameterObject ShowSearchPaginationApiRequest request
    ) {
        var response = showService.searchShow(request.toServiceRequest());
        var data = response.data().stream()
            .map(ShowSearchPaginationApiParam::from)
            .toList();

        CursorApiResponse cursor = Optional.ofNullable(CursorApiResponse.getLastElement(data))
            .map(element -> CursorApiResponse.toCursorId(element.id()))
            .orElse(CursorApiResponse.noneCursor());

        return SuccessResponse.ok(
            PaginationApiResponse.<ShowSearchPaginationApiParam>builder()
                .hasNext(response.hasNext())
                .data(data)
                .cursor(cursor)
                .build()
        );
    }
}
