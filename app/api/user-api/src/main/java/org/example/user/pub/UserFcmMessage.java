package org.example.user.pub;

import java.util.UUID;

public record UserFcmMessage(
    UUID userId,
    String previousFcmToken,
    String updatedFcmToken
) {

    public static UserFcmMessage of(UUID userId, String previousFcmToken, String updatedFcmToken) {
        return new UserFcmMessage(userId, previousFcmToken, updatedFcmToken);
    }
}
