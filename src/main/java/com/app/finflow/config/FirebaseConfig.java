package com.app.finflow.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import io.github.cdimascio.dotenv.Dotenv;

@Slf4j
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            String firebaseJson = System.getenv("FIREBASE_SERVICE_ACCOUNT");
            if ((firebaseJson == null || firebaseJson.isEmpty()) && dotenv != null) {
                firebaseJson = dotenv.get("FIREBASE_SERVICE_ACCOUNT");
            }

            if (firebaseJson == null || firebaseJson.isEmpty()) {
                throw new IllegalStateException("Missing FIREBASE_SERVICE_ACCOUNT");
            }

            InputStream serviceAccount = new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8));
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase initialized successfully");
            }

        } catch (Exception e) {
            log.error("Firebase initialization failed", e);
        }
    }
}
