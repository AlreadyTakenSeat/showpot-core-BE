package com.example.artist.controller.dto.param;

import com.example.artist.service.dto.param.ArtistSubscriptionPaginationServiceParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record ArtistSubscriptionPaginationApiParam(
    @Schema(description = "아티스트 ID")
    UUID id,
    @Schema(description = "아티스트 이미지 URL")
    String imageUrl,
    @Schema(description = "아티스트 한글 이름")
    String koreanName,
    @Schema(description = "아티스트 영문 이름")
    String englishName
) {

    public static ArtistSubscriptionPaginationApiParam from(ArtistSubscriptionPaginationServiceParam param) {
        return new ArtistSubscriptionPaginationApiParam(
            param.artistId(),
            param.artistImageUrl(),
            param.artistKoreanName(),
            param.artistEnglishName()
        );
    }
}