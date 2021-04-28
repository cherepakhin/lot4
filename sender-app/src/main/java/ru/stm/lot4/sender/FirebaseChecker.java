package ru.stm.lot4.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.scheduling.annotation.Scheduled;
import ru.stm.lot4.dto.PushNotificationDto;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * В зависимости от состояния соединение с Firebase,
 * останавливает или запускает прием сообщений из Kafka
 */
@Slf4j
public class FirebaseChecker implements IFirebaseChecker {

    private final KafkaMessageListenerContainer<String, PushNotificationDto> kafkaContainer;
    private final String urlFirebase;

    public FirebaseChecker(KafkaMessageListenerContainer<String, PushNotificationDto> kafkaContainer,
                           String urlFirebase) {
        this.kafkaContainer = kafkaContainer;
        this.urlFirebase = urlFirebase;
    }

    @Scheduled(fixedRateString = "${fcm.health_timeout}")
    public void check() {
        if (isOkFirebase()) {
            resume();
        } else {
            pause("Pause!!!. Firebase not connected");
        }
    }

    boolean isOkFirebase() {
        // Простой ping, без наворотов
        try {
            URLConnection connection = new URL(urlFirebase).openConnection();
            connection.connect();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void pause(String cause) {
        log.error(cause);
        kafkaContainer.pause();
    }

    public void resume() {
        kafkaContainer.resume();
    }


}
