package com.spike.fireabsepushnotifications.clients;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FirebaseMessagingClient implements MessagingClient {

    private final FirebaseMessaging firebaseMessaging;

    private static final String BODY_KEY = "body";

    public String sendMessage(final String topic, final String body) {
        final Message msg = Message
                .builder()
                .setTopic(topic)
                .putData(BODY_KEY, body)
                .build();
        try {
            return firebaseMessaging.send(msg);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendMessageToClient(final String token, final String body) {
        final Message msg = Message
                .builder()
                .setToken(token)
                .putData(BODY_KEY, body)
                .build();
        try {
            return firebaseMessaging.send(msg);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
