package com.example.artist.service.dto.response;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ArtistSubscriptionServiceResponse(
    List<UUID> successSubscriptionArtistIds
) {

}