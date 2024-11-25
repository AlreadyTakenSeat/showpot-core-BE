package com.example.artist.service.dto.response;

import java.util.UUID;
import org.example.entity.artist.Artist;

public record ArtistIdsServiceResponse(
    UUID id,
    String artistSpotifyId
) {

    public static ArtistIdsServiceResponse from(Artist artist) {
        return new ArtistIdsServiceResponse(artist.getId(), artist.getSpotifyId());
    }
}
