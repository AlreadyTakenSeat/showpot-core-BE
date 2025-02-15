package org.example.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.example.user.service.dto.response.UserProfileServiceResponse;
import org.example.user.vo.SocialLoginApiType;

@Builder
public record UserProfileApiResponse(

    @Schema(description = "닉네임")
    String nickname,

    @Schema(description = "소셜 로그인 타입")
    SocialLoginApiType type
) {

    public static UserProfileApiResponse from(UserProfileServiceResponse profile) {
        return UserProfileApiResponse.builder()
            .nickname(profile.nickname())
            .type(profile.type())
            .build();
    }

}
