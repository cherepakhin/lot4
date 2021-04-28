package ru.stm.lot4.sender;

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
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.stm.lot4.dto.PushNotificationDto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
@EnableKafka
public class SenderConfig {
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Value("${kafka.host}")
    private String kafkaHost;
    @Value("${kafka.namegroup}")
    private String nameGroup;
    @Value("${kafka.topic}")
    private String topic;

    @Value("${fcm.service-account-key}")
    private String fcmServiceAccountKey;
    @Value("${fcm.count-thread}")
    private Integer fcmCountThread;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
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
    public ConsumerFactory<String, PushNotificationDto> consumerFactory() {
        final JsonDeserializer<PushNotificationDto> jsonDeserializer = new JsonDeserializer<>(PushNotificationDto.class);
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
    public ConcurrentKafkaListenerContainerFactory<String, PushNotificationDto> kafkaListenerContainerFactory(
            KafkaErrorHandler kafkaErrorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, PushNotificationDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(kafkaErrorHandler);
        return factory;
    }

    @Bean
    FirebaseSenderService firebaseSenderService() {
        ExecutorService executors = Executors.newFixedThreadPool(fcmCountThread);
        return new FirebaseSenderService(fcmServiceAccountKey, executors, FIREBASE_API_URL);
    }

    /**
     * Привязка Kafka-listener к сервису отправки событий в FirebaseSenderService
     */
    @Bean
    public KafkaMessageListenerContainer<String, PushNotificationDto> firebaseConsumerContainer(
            FirebaseSenderService firebaseSenderService,
            ConsumerFactory<String, PushNotificationDto> consumerFactory,
            KafkaErrorHandler kafkaErrorHandler) {
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setGroupId(nameGroup);
        KafkaConsumer kafkaConsumer = new KafkaConsumer(firebaseSenderService);
        containerProps.setMessageListener(kafkaConsumer);
        KafkaMessageListenerContainer<String, PushNotificationDto> container =
                new KafkaMessageListenerContainer<>(consumerFactory, containerProps);
        container.setErrorHandler(kafkaErrorHandler);
        return container;
    }

    @Bean
    IFirebaseChecker firebaseChecker(KafkaMessageListenerContainer<String, PushNotificationDto>
                                             firebaseConsumerContainer, FirebaseSenderService firebaseSenderService) {
        FirebaseChecker checker = new FirebaseChecker(firebaseConsumerContainer, FIREBASE_API_URL);
        firebaseSenderService.setFirebaseChecker(checker);
        return checker;
    }
}
