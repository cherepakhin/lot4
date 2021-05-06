package ru.stm.lot4.sender;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import ru.stm.lot4.dto.PushNotificationDto;

public class KafkaPushNotificationDtoConsumer implements MessageListener<String, PushNotificationDto> {

    FirebaseSenderService firebaseSenderService;

    public KafkaPushNotificationDtoConsumer(FirebaseSenderService firebaseSenderService) {
        this.firebaseSenderService = firebaseSenderService;
    }

    @Override
    public void onMessage(ConsumerRecord<String, PushNotificationDto> record) {
        firebaseSenderService.sendAsync(record.value());
    }

}
