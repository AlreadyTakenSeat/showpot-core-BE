package org.example.vo;

import java.util.Arrays;
import java.util.List;

public enum ArtistFilterType {

    K_START("k-"), KOREAN("korean"), K_TROT("trot");

    private final String value;

    ArtistFilterType(String value) {
        this.value = value;
    }

    public static boolean isForeignArtist(List<String> genres) {
        if (genres.isEmpty()) {
            return false;
        }

        return genres.stream()
            .noneMatch(genre -> Arrays.stream(values())
                .anyMatch(filter -> genre.contains(filter.value)));
    }
}
