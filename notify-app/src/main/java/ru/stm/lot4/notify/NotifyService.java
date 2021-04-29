package ru.stm.lot4.notify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import ru.stm.lot4.dto.PushNotificationRequest;

import java.util.UUID;

@Slf4j
public class NotifyService {

    private final String topic;
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper mapper = new ObjectMapper();

    public NotifyService(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    String sendRequest(PushNotificationRequest pushNotificationRequest) {
        String uuid = UUID.randomUUID().toString();
        pushNotificationRequest.setId(uuid);
        try {
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate
                    .send(topic, mapper.writeValueAsString(pushNotificationRequest));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return uuid;
    }
}
