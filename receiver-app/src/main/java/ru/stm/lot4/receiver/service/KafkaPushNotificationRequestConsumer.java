package ru.stm.lot4.receiver.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import ru.stm.lot4.dto.PushNotificationRequest;

public class KafkaPushNotificationRequestConsumer implements MessageListener<String, PushNotificationRequest> {

    PushNotificationService pushNotificationService;

    public KafkaPushNotificationRequestConsumer(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @Override
    public void onMessage(ConsumerRecord<String, PushNotificationRequest> record) {
        pushNotificationService.saveRequest(record.value());
    }

}
