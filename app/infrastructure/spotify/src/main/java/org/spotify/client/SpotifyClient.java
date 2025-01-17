package org.spotify.client;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.metric.OpenApiMonitored;
import org.spotify.client.dto.request.AccessTokenSpotifyRequest;
import org.spotify.client.dto.request.ArtistSearchSpotifyRequest;
import org.spotify.client.dto.request.ArtistsSpotifyRequest;
import org.spotify.client.dto.response.SpotifyAccessTokenResponse;
import org.spotify.client.dto.response.SpotifyArtistsResponse;
import org.spotify.client.dto.response.SpotifySearchResponse;
import org.spotify.property.SpotifyProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpotifyClient {

    private final SpotifyProperty spotifyProperty;

    @OpenApiMonitored(name = "accessToken")
    public String requestAccessToken() {
        ResponseEntity<SpotifyAccessTokenResponse> result = RestClient.create(spotifyProperty.tokenApiURL())
            .post()
            .contentType(APPLICATION_FORM_URLENCODED)
            .body(
                AccessTokenSpotifyRequest.builder()
                    .clientId(spotifyProperty.clientId())
                    .clientSecret(spotifyProperty.clientSecret())
                    .build()
                    .toHttpRequestMap()
            )
            .retrieve()
            .toEntity(SpotifyAccessTokenResponse.class);

        if (result.getBody() == null || !result.getStatusCode().is2xxSuccessful()
        ) {
            log.error("Spotify API artist access token failed: {}", result);
            throw new RuntimeException("Spotify API artist access token failed");
        }

        return result.getBody().accessToken();
    }

    @OpenApiMonitored(name = "search")
    public SpotifySearchResponse searchArtist(ArtistSearchSpotifyRequest request) {
        ResponseEntity<SpotifySearchResponse> result =
            RestClient.create(spotifyProperty.apiURL() + "/search?" + request.toQueryParameter())
            .get()
            .header("Authorization", "Bearer " + request.accessToken())
            .retrieve()
            .toEntity(SpotifySearchResponse.class);

        if (result.getStatusCode() == HttpStatus.UNAUTHORIZED
            || result.getStatusCode() == HttpStatus.FORBIDDEN
            || result.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS
        ) {
            log.error("Spotify API search artist failed: {}", result);
            throw new RuntimeException("Spotify API artist search artist failed");
        }

        return result.getBody();
    }

    @OpenApiMonitored(name = "artists")
    public SpotifyArtistsResponse findArtistsBySpotifyArtistId(ArtistsSpotifyRequest request) {
        ResponseEntity<SpotifyArtistsResponse> result =
            RestClient.create(spotifyProperty.apiURL() + "/artists?" + request.toQueryParameter())
            .get()
            .header("Authorization", "Bearer " + request.accessToken())
            .retrieve()
            .toEntity(SpotifyArtistsResponse.class);

        if (result.getStatusCode() == HttpStatus.UNAUTHORIZED
            || result.getStatusCode() == HttpStatus.FORBIDDEN
            || result.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS
        ) {
            log.error("Spotify API find artists failed: {}", result);
            throw new RuntimeException("Spotify API artist find artists failed");
        }

        return result.getBody();
    }
}
