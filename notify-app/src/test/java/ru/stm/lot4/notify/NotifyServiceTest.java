package ru.stm.lot4.notify;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import ru.stm.lot4.dto.PushNotificationRequest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class NotifyServiceTest {

    KafkaTemplate<String, PushNotificationRequest> kafkaTemplate = mock(KafkaTemplate.class);

    @Test
    void sendRequest() throws JsonProcessingException {
        NotifyService notifyService = new NotifyService(kafkaTemplate, "TOPIC");
        PushNotificationRequest request = new PushNotificationRequest();
        request.setTitle("TITLE");
        String uuid = notifyService.sendRequest(request);
        assertFalse(uuid.isEmpty());
        request.setId(uuid);
        verify(kafkaTemplate, times(1))
                .send("TOPIC", request);
    }

}