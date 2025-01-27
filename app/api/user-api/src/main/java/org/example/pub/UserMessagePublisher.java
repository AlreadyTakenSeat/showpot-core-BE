package org.example.pub;

public interface UserMessagePublisher {

    void publishFcmToken(String topic, UserFcmMessage message);
}
