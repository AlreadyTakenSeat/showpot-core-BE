package org.example.user.pub;

public interface UserMessagePublisher {

    void publishFcmToken(String topic, UserFcmMessage message);
}
