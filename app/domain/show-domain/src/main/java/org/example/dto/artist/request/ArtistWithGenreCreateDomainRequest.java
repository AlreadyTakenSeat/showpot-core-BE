package org.example.dto.artist.request;

import java.util.List;

public record ArtistWithGenreCreateDomainRequest(
    List<ArtistGenreDomainRequest> artistGenres
) {

    public List<String> getGenreNames() {
        return artistGenres.stream()
            .map(ArtistGenreDomainRequest::genreName)
            .toList();
    }

}
