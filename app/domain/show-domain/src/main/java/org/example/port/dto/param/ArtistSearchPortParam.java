package org.example.port.dto.param;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import org.example.dto.artist.request.ArtistGenreDomainRequest;
import org.example.dto.artist.response.ArtistSearchSimpleDomainResponse;
import org.example.entity.artist.Artist;
import org.example.vo.ArtistGenreType;

@Builder
public record ArtistSearchPortParam(
    String id,
    String name,
    List<String> genres,
    String imageURL
) {

    public ArtistSearchSimpleDomainResponse toDomainResponse(Artist artist) {
        return ArtistSearchSimpleDomainResponse.builder()
            .id(artist != null ? artist.getId() : null)
            .name(name)
            .image(imageURL)
            .spotifyId(id)
            .build();
    }

    public Artist toArtist() {
        return Artist.builder()
            .name(name)
            .spotifyId(id)
            .image(imageURL)
            .build();
    }

    public ArtistGenreDomainRequest toDomainRequest(UUID artistId) {
        String mappedGenres = genres.stream()
            .map(ArtistGenreType::findByGenreClassificationName)
            .filter(Objects::nonNull)
            .findFirst()
            .orElseGet(ArtistGenreType.POP::name)
            .toLowerCase();

        return ArtistGenreDomainRequest.builder()
            .artistId(artistId)
            .name(name)
            .image(imageURL)
            .spotifyId(id)
            .genreName(mappedGenres)
            .build();
    }
}
