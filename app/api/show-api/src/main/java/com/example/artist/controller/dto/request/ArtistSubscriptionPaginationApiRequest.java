package com.example.artist.controller.dto.request;

import com.example.artist.service.dto.request.ArtistSubscriptionPaginationServiceRequest;
import com.example.artist.vo.ArtistSortStandardApiType;
import com.example.vo.SubscriptionStatusApiType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema
public record ArtistSubscriptionPaginationApiRequest(

    @Parameter(
        description = "정렬 기준, default: ENGLISH_NAME_ASC",
        schema = @Schema(implementation = ArtistSortStandardApiType.class)
    )
    ArtistSortStandardApiType sortStandard,

    @Parameter(description = "이전 페이지네이션 마지막 데이터의 ID / 최초 조회라면 null")
    UUID cursor,

    @Parameter(description = "조회하는 데이터 개수", required = true)
    int size
) {

    public ArtistSubscriptionPaginationApiRequest(
        ArtistSortStandardApiType sortStandard,
        UUID cursor,
        int size
    ) {
        this.sortStandard =
            sortStandard == null ? ArtistSortStandardApiType.ENGLISH_NAME_ASC : sortStandard;
        this.cursor = cursor;
        this.size = size;
    }

    public ArtistSubscriptionPaginationServiceRequest toServiceRequest(UUID userId) {
        return ArtistSubscriptionPaginationServiceRequest.builder()
            .subscriptionStatusApiType(SubscriptionStatusApiType.SUBSCRIBED)
            .size(size)
            .sortStandard(sortStandard)
            .cursor(cursor)
            .userId(userId)
            .build();
    }
}