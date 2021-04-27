package ru.stm.lot4.receiver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.stm.lot4.dto.PushNotificationDto;

public interface PushNotificationSenderService {
    void send(PushNotificationDto pushNotificationDto,
              ListenableFutureCallback<SendResult<String, String>> callback) throws JsonProcessingException;
}
