package ru.stm.lot4.receiver.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.receiver.service.PushNotificationSenderService;

@Slf4j
public class PushNotificationKafkaSenderServiceImpl implements PushNotificationSenderService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String topic;

    public PushNotificationKafkaSenderServiceImpl(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void send(PushNotificationDto pushNotificationDto,
                     ListenableFutureCallback<SendResult<String, String>> callback) throws JsonProcessingException {
        if (pushNotificationDto == null) {
            return;
        }
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate
                .send(topic, objectMapper.writeValueAsString(pushNotificationDto));
        if (callback != null) {
            future.addCallback(callback);
        }
    }

}
