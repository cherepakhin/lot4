package ru.stm.lot4.notify;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.stm.lot4.dto.PushNotificationRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    ObjectMapper mapper = new ObjectMapper();
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value(value = "${kafka.topic}")
    private String topic;

    @Bean
    public ProducerFactory<String, PushNotificationRequest> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        JsonSerializer<PushNotificationRequest> jsonSerializer = new JsonSerializer<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                jsonSerializer);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, PushNotificationRequest> kafkaTemplate(ProducerFactory<String, PushNotificationRequest> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NotifyService notifyService(KafkaTemplate<String, PushNotificationRequest> kafkaTemplate) {
        return new NotifyService(kafkaTemplate, topic);
    }
}
