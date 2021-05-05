package ru.stm.lot4.receiver.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;
import ru.stm.lot4.dto.PushNotificationRequest;
import ru.stm.lot4.receiver.service.PushNotificationRequestConsumer;
import ru.stm.lot4.receiver.service.PushNotificationService;

@Service
public class PushNotificationRequestConsumerImpl implements
        MessageListener<String, PushNotificationRequest>,
        PushNotificationRequestConsumer {

    private PushNotificationService pushNotificationService;

    public PushNotificationRequestConsumerImpl(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @Override
    public void onMessage(ConsumerRecord<String, PushNotificationRequest> record) {
        consume(record.value());
    }

    @Override
    public void consume(PushNotificationRequest pushNotificationRequest) {
        pushNotificationService.saveRequest(pushNotificationRequest);
    }
}
