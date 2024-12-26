package com.example.artist.controller.dto.response;

import com.example.artist.service.dto.response.ArtistIdsServiceResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record ArtistIdsApiResponse(
    @Schema(description = "아티스트 ID")
    UUID id,

    @Schema(description = "아티스트의 스포티파이 ID")
    String spotifyId
) {

    public static ArtistIdsApiResponse from(ArtistIdsServiceResponse response) {
        return new ArtistIdsApiResponse(response.id(), response.artistSpotifyId());
    }
}
