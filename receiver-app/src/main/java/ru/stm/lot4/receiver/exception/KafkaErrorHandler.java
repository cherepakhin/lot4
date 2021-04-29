package ru.stm.lot4.receiver.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.serializer.DeserializationException;

@Slf4j
public class KafkaErrorHandler implements org.springframework.kafka.listener.ErrorHandler {

    @Override
    public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {
        log.error("Error mapping json {} {}", e, consumerRecord);
    }

    @Override
    public void handle(Exception e, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
        if (e instanceof DeserializationException) {
            DeserializationException exception = (DeserializationException) e;
            String dataStr = new String(exception.getData());
            log.error("Error mapping json {}", dataStr);
        } else {
            log.error(e.getMessage());
        }
    }
}
