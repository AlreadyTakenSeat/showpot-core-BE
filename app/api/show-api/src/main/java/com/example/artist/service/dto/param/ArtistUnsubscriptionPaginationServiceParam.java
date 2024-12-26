package com.example.artist.service.dto.param;

import java.util.UUID;
import org.example.dto.artist.response.ArtistSimpleDomainResponse;

public record ArtistUnsubscriptionPaginationServiceParam(
    UUID id,
    String spotifyId,
    String image,
    String name
) {

    public ArtistUnsubscriptionPaginationServiceParam(ArtistSimpleDomainResponse response) {
        this(
            response.id(),
            response.spotifyId(),
            response.image(),
            response.name()
        );
    }
}
