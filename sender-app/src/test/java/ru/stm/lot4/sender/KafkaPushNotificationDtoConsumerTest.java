package ru.stm.lot4.sender;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import ru.stm.lot4.dto.PushNotificationDto;

import static org.mockito.Mockito.*;

public class KafkaPushNotificationDtoConsumerTest {

    FirebaseSenderService firebaseSenderService = mock(FirebaseSenderService.class);

    @Test
    void isCalledService() {
        PushNotificationDto dto = new PushNotificationDto();
        KafkaPushNotificationDtoConsumer kafkaPushNotificationDtoConsumer = new KafkaPushNotificationDtoConsumer(firebaseSenderService);
        kafkaPushNotificationDtoConsumer.onMessage(
                new ConsumerRecord<String, PushNotificationDto>("topic", 1, 1L, "KEY", dto));

        verify(firebaseSenderService, times(1)).sendAsync(dto);
    }

}