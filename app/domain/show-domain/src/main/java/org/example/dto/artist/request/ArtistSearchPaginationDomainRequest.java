package org.example.dto.artist.request;

import java.util.UUID;
import lombok.Builder;
import org.example.vo.ArtistSortType;

@Builder
public record ArtistSearchPaginationDomainRequest(

    UUID userId,
    ArtistSortType sortStandard,
    UUID cursor,
    int size,
    String search
) {

}
