package org.example.dto.artist.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ArtistSimpleDomainResponse(
    UUID id,
    String spotifyId,
    String name,
    String image
) {

}
