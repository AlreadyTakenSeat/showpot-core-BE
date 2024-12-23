package show.fixture.dto;

import com.example.artist.service.dto.request.ArtistSubscriptionServiceRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class ArtistRequestDtoFixture {

    public static ArtistSubscriptionServiceRequest artistSubscriptionServiceRequest(int count) {
        return new ArtistSubscriptionServiceRequest(
            spotifyArtistIds(count),
            UUID.randomUUID()
        );
    }

    public static List<String> spotifyArtistIds(int count) {
        return Stream.generate(() -> "spotifyId")
            .limit(count)
            .toList();
    }
}