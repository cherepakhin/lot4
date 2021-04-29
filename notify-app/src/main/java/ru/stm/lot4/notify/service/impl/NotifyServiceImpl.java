package ru.stm.lot4.notify.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import ru.stm.lot4.dto.PushNotificationRequest;
import ru.stm.lot4.notify.service.NotifyService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    @Value(value = "${kafka.topic}")
    private String topic;

    @Override
    public String sendRequest(PushNotificationRequest pushNotificationRequest) {
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
