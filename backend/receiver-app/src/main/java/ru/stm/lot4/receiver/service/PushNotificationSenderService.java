package ru.stm.lot4.receiver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.stm.lot4.dto.PushNotificationDto;

/**
 * Сервис отправки push-уведомлений в кафку
 */
public interface PushNotificationSenderService {
    /**
     * Метод отправки push-уведомлений в кафку
     *
     * @param pushNotificationDto - push-уведомление
     * @param callback - каллбэк в случае успешной или неуспешно отправки данных в кафку
     * @throws JsonProcessingException - Ошибка преобразования pushNotificationDto в json
     */
    void send(PushNotificationDto pushNotificationDto,
              ListenableFutureCallback<SendResult<String, String>> callback) throws JsonProcessingException;
}
