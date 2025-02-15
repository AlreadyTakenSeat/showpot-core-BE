package org.example.user.service.dto.request;

import lombok.Builder;
import org.example.dto.request.LoginDomainRequest;
import org.example.entity.User;
import org.example.user.vo.SocialLoginApiType;
import org.example.vo.RandomNickname;

@Builder
public record LoginServiceRequest(
    SocialLoginApiType socialLoginType,
    String identifier,
    String fcmToken
) {

    public User createUser(String profileUrl) {
        return User.builder()
            .nickname(RandomNickname.makeRandomNickName())
            .fcmToken(fcmToken)
            .profileUrl(profileUrl)
            .build();
    }

    public LoginDomainRequest toDomainRequest() {
        return LoginDomainRequest.builder()
            .socialLoginType(socialLoginType.toDomainType())
            .identifier(identifier)
            .fcmToken(fcmToken)
            .build();
    }
}
