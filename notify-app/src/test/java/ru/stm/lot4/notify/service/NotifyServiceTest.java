package ru.stm.lot4.notify.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import ru.stm.lot4.dto.PushNotificationRequest;
import ru.stm.lot4.notify.service.impl.NotifyServiceImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class NotifyServiceTest {

    KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void sendRequest() throws JsonProcessingException {
        NotifyService notifyService = new NotifyServiceImpl(kafkaTemplate, mapper);
        ReflectionTestUtils.setField(notifyService, "topic", "TEST");
        PushNotificationRequest request = new PushNotificationRequest();
        request.setTitle("TITLE");
        String uuid = notifyService.sendRequest(request);
        assertFalse(uuid.isEmpty());
        request.setId(uuid);
        String json=mapper.writeValueAsString(request);
        verify(kafkaTemplate, times(1))
                .send("TOPIC", json);
    }

}