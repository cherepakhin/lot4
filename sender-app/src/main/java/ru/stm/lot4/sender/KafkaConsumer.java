package ru.stm.lot4.sender;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import ru.stm.lot4.dto.PushNotificationDto;

public class KafkaConsumer implements MessageListener<String, PushNotificationDto> {

    FirebaseSenderService firebaseSenderService;

    public KafkaConsumer(FirebaseSenderService firebaseSenderService) {
        this.firebaseSenderService = firebaseSenderService;
    }

    @Override
    public void onMessage(ConsumerRecord<String, PushNotificationDto> record) {
        firebaseSenderService.send(record.value());
    }

}
