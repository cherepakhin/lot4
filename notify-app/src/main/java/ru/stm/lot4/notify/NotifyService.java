package ru.stm.lot4.notify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import ru.stm.lot4.dto.PushNotificationRequest;

import java.util.UUID;

@Slf4j
public class NotifyService {

    private final String topic;
    KafkaTemplate<String, PushNotificationRequest> kafkaTemplate;

    public NotifyService(KafkaTemplate<String, PushNotificationRequest> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    String sendRequest(PushNotificationRequest pushNotificationRequest) {
        String uuid = UUID.randomUUID().toString();
        pushNotificationRequest.setId(uuid);
        ListenableFuture<SendResult<String, PushNotificationRequest>> future = kafkaTemplate
                .send(topic, pushNotificationRequest);
        return uuid;
    }
}
