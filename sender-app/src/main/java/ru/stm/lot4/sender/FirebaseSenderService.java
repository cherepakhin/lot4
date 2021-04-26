package ru.stm.lot4.sender;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.stm.lot4.dto.PushNotificationDto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class FirebaseSenderService {

    public FirebaseSenderService(String fcmServiceAccountFile) {
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

    public void send(PushNotificationDto pushNotificationDto) {
        pushNotificationDto.getPhones().stream().forEach(phoneDto -> send(phoneDto.getApp().getToken());
        Message message = Message.builder().setToken(pushNotificationDto.)
                .setWebpushConfig(WebpushConfig.builder()
                        .putHeader("ttl", conf.getTtlInSeconds())
                        .setNotification(createBuilder(conf).build())
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance()
                .sendAsync(message)
                .get();
    }
}
