package artist.fixture.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.example.dto.artist.response.ArtistPaginationDomainResponse;
import org.example.dto.artist.response.ArtistSearchPaginationDomainResponse;
import org.example.dto.artist.response.ArtistSearchSimpleDomainResponse;
import org.example.dto.artist.response.ArtistSimpleDomainResponse;

public class ArtistResponseDtoFixture {

    public static ArtistPaginationDomainResponse artistPaginationDomainResponse(
        int size,
        boolean hasNext
    ) {
        return ArtistPaginationDomainResponse.builder()
            .data(artistSimpleDomainResponses(size))
            .hasNext(hasNext)
            .build();
    }

    public static ArtistPaginationDomainResponse emptyDataArtistPaginationDomainResponse() {
        return ArtistPaginationDomainResponse.builder()
            .data(List.of())
            .hasNext(false)
            .build();
    }

    public static List<ArtistSimpleDomainResponse> artistSimpleDomainResponses(
        int size
    ) {
        return IntStream.range(0, size)
            .mapToObj(i -> ArtistSimpleDomainResponse.builder()
                .id(UUID.randomUUID())
                .name(i + "name")
                .image(i + "testImage")
                .build())
            .toList();
    }

    public static ArtistSearchPaginationDomainResponse artistSearchPaginationDomainResponse(
        int limit,
        boolean hasNext
    ) {
        return ArtistSearchPaginationDomainResponse.builder()
            .data(artistSearchSimpleDomainResponses(limit))
            .limit(limit)
            .offset(0)
            .hasNext(hasNext)
            .build();

    }

    public static List<ArtistSearchSimpleDomainResponse> artistSearchSimpleDomainResponses(
        int size
    ) {
        return Stream.generate(() -> ArtistSearchSimpleDomainResponse.builder()
                .id(UUID.randomUUID())
                .name("name")
                .image("testImage")
                .build())
            .limit(size)
            .toList();
    }
}
