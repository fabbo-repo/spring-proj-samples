package com.spyke.firestore.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class FirebaseInit {
    @PostConstruct
    public void init() {
        final FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream("serviceAccountKey.json");
            final FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
