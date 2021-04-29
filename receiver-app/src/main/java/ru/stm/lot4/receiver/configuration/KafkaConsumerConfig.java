package ru.stm.lot4.receiver.configuration;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.stm.lot4.dto.PushNotificationRequest;
import ru.stm.lot4.receiver.exception.KafkaErrorHandler;
import ru.stm.lot4.receiver.service.impl.PushNotificationRequestConsumerImpl;
import ru.stm.lot4.receiver.service.PushNotificationService;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value("${kafka.group_notify}")
    private String nameGroup;
    @Value("${kafka.topic_notify}")
    private String topic;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    /**
     * Factory для читателя Kafka
     */
    @Bean
    public ConsumerFactory<String, PushNotificationRequest> consumerFactory() {
        final JsonDeserializer<PushNotificationRequest> jsonDeserializer =
                new JsonDeserializer<>(PushNotificationRequest.class);
        jsonDeserializer.addTrustedPackages("*");
        ErrorHandlingDeserializer errorHandlingDeserializer
                = new ErrorHandlingDeserializer<>(jsonDeserializer);
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                jsonDeserializer,
                errorHandlingDeserializer);
    }

    @Bean
    public KafkaErrorHandler kafkaErrorHandler() {
        return new KafkaErrorHandler();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PushNotificationRequest> kafkaListenerContainerFactory(
            KafkaErrorHandler kafkaErrorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, PushNotificationRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(kafkaErrorHandler);
        return factory;
    }

    /**
     * Привязка Kafka-listener к сервису отправки событий в FirebaseSenderService
     */
    @Bean
    public KafkaMessageListenerContainer<String, PushNotificationRequest> firebaseConsumerContainer(
            PushNotificationService pushNotificationService,
            ConsumerFactory<String, PushNotificationRequest> consumerFactory,
            KafkaErrorHandler kafkaErrorHandler) {
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setGroupId(nameGroup);
        PushNotificationRequestConsumerImpl kafkaConsumer =
                new PushNotificationRequestConsumerImpl(pushNotificationService);
        containerProps.setMessageListener(kafkaConsumer);
        KafkaMessageListenerContainer<String, PushNotificationRequest> container =
                new KafkaMessageListenerContainer<>(consumerFactory, containerProps);
        container.setErrorHandler(kafkaErrorHandler);
        return container;
    }
}
