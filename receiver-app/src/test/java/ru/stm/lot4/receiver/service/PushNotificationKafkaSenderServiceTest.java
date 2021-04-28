package ru.stm.lot4.receiver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.util.ReflectionTestUtils;
import ru.stm.lot4.dto.MobileApplicationDto;
import ru.stm.lot4.dto.PhoneDto;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.receiver.service.impl.PushNotificationKafkaSenderServiceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PushNotificationKafkaSenderServiceTest {

    private static String KAFKA_TOPIC = "test";

    private KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
    private PushNotificationSenderService pushNotificationSenderService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        pushNotificationSenderService = new PushNotificationKafkaSenderServiceImpl(kafkaTemplate, objectMapper);
        ReflectionTestUtils.setField(pushNotificationSenderService, "topic", KAFKA_TOPIC);
    }

    @Test
    public void test_send_to_kafka() throws JsonProcessingException {
        MobileApplicationDto mobileApplicationDto = new MobileApplicationDto()
                .setVersion("1.0");
        PhoneDto phoneDto = new PhoneDto()
                .setToken("token")
                .setApp(mobileApplicationDto)
                .setNumber("+79999999999");
        PushNotificationDto pushNotificationDto = new PushNotificationDto()
                .setBody("body")
                .setDate(new Date())
                .setPhones(Collections.singleton(phoneDto))
                .setTitle("title");
        pushNotificationSenderService.send(pushNotificationDto, null);
        verify(kafkaTemplate, times(1)).send(KAFKA_TOPIC, objectMapper.writeValueAsString(pushNotificationDto));
    }
}
