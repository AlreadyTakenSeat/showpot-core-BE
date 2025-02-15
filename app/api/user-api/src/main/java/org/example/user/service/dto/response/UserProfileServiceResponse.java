package org.example.user.service.dto.response;

import lombok.Builder;
import org.example.dto.response.UserProfileDomainResponse;
import org.example.user.vo.SocialLoginApiType;

@Builder
public record UserProfileServiceResponse(
    String nickname,
    SocialLoginApiType type
) {

    public static UserProfileServiceResponse from(UserProfileDomainResponse profile) {
        return UserProfileServiceResponse.builder()
            .nickname(profile.nickname())
            .type(SocialLoginApiType.from(profile.type()))
            .build();
    }
}
