package com.example.artist.controller.dto.response;

import com.example.artist.service.dto.response.ArtistSubscriptionServiceResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ArtistSubscriptionApiResponse(
    @Schema(description = "구독한 아티스트 아이디 목록")
    List<ArtistIdsApiResponse> subscriptionArtistIds
) {

    public static ArtistSubscriptionApiResponse from(ArtistSubscriptionServiceResponse response) {
        return new ArtistSubscriptionApiResponse(response.subscriptionArtistIds()
            .stream()
            .map(ArtistIdsApiResponse::from)
            .toList()
        );
    }
}
