package com.example.artist.service.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ArtistSubscriptionServiceResponse(
    List<ArtistIdsServiceResponse> subscriptionArtistIds
) {

}
