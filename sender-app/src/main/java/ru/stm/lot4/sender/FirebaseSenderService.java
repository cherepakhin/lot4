package ru.stm.lot4.sender;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.extern.slf4j.Slf4j;
import ru.stm.lot4.dto.PushNotificationDto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

@Slf4j
public class FirebaseSenderService {

    private String ttl;

    public FirebaseSenderService(String fcmServiceAccountFile, String ttl) {
        this.ttl = ttl;
        Path p = Paths.get(fcmServiceAccountFile);
        try (InputStream serviceAccount = Files.newInputStream(p)) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void send(PushNotificationDto dto) {
        dto.getPhones().stream().forEach(phoneDto ->
                send(phoneDto.getToken(), dto.getTitle(), dto.getBody()));
    }

    private void send(String token, String title, String body) {
        Message message = Message.builder().setToken(token)
                .setWebpushConfig(WebpushConfig.builder()
                        .putHeader("ttl", ttl)
                        .setNotification(new WebpushNotification(title, body))
                        .build())
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance()
                    .sendAsync(message)
                    .get();
            log.info(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
