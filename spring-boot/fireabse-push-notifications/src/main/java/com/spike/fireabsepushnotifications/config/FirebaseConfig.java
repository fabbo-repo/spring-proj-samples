package com.spike.fireabsepushnotifications.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    private static final String SERVICE_ACCOUNT_FILE_PATH = "serviceAccountKey.json";

    @Bean
    GoogleCredentials googleCredentials() {
        final FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream(SERVICE_ACCOUNT_FILE_PATH);
            return GoogleCredentials.fromStream(serviceAccount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    FirebaseApp firebaseApp(final GoogleCredentials credentials) {
        final FirebaseOptions options = FirebaseOptions
                .builder()
                .setCredentials(credentials)
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    FirebaseMessaging firebaseMessaging(final FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
