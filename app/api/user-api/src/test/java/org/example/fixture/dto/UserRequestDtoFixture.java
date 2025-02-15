package org.example.fixture.dto;

import org.example.user.service.dto.request.LoginServiceRequest;
import org.example.user.vo.SocialLoginApiType;

public class UserRequestDtoFixture {

    public static LoginServiceRequest loginServiceRequest(SocialLoginApiType type) {
        return LoginServiceRequest.builder()
            .socialLoginType(type)
            .fcmToken("testFcmToken")
            .identifier("testIdentifier")
            .build();
    }
}
