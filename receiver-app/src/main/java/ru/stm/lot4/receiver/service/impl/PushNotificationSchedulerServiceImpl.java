package ru.stm.lot4.receiver.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;
import ru.stm.lot4.receiver.mapper.PushNotificationMapper;
import ru.stm.lot4.receiver.service.PushNotificationSchedulerService;
import ru.stm.lot4.receiver.service.PushNotificationSenderService;
import ru.stm.lot4.receiver.service.PushNotificationService;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PushNotificationSchedulerServiceImpl implements PushNotificationSchedulerService {

    private final PushNotificationService pushNotificationService;
    private final PushNotificationMapper mapper;
    private final PushNotificationSenderService pushNotificationSenderService;

    @Value("${receiver.schedule.rate}")
    private int timeScheduleRate = 1;

    public PushNotificationSchedulerServiceImpl(PushNotificationService pushNotificationService,
                                                ScheduledExecutorService scheduledExecutorService,
                                                PushNotificationMapper mapper, PushNotificationSenderService pushNotificationSenderService) {
        this.pushNotificationService = pushNotificationService;
        this.mapper = mapper;
        this.pushNotificationSenderService = pushNotificationSenderService;
        scheduledExecutorService.scheduleAtFixedRate(this::sendPushNotificationToKafka, 0, timeScheduleRate, TimeUnit.SECONDS);
    }

    @Override
    public void sendPushNotificationToKafka() {
        List<PushNotificationEntity> pushNotificationList = pushNotificationService.receiveActualAvailablePushNotification();
        if (pushNotificationList.isEmpty()) {
            return;
        }
        log.info("receive available pushNotification. Size: {}", pushNotificationList.size());
        pushNotificationList.forEach(this::sendToKafka);
    }


    private void sendToKafka(PushNotificationEntity pushNotificationEntity) {
        try {
            log.info("send");
            PushNotificationDto pushNotificationDto = mapper.toDTO(pushNotificationEntity);
            pushNotificationSenderService.send(pushNotificationDto, receiveCallback(pushNotificationEntity));
        } catch (JsonProcessingException e) {
            handleException(e, pushNotificationEntity);
        }
    }

    private ListenableFutureCallback<SendResult<String, String>> receiveCallback(PushNotificationEntity pushNotificationEntity) {
        return new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                handleException(throwable, pushNotificationEntity);
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.debug("success send pushNotification to kafka. ID = {}",
                        pushNotificationEntity.getId());
            }
        };
    }

    private void handleException(Throwable throwable, PushNotificationEntity pushNotificationEntity) {
        log.error("Error send pushNotification. ID = {}",
                pushNotificationEntity.getId(),
                throwable);
        pushNotificationEntity.setStatus(PushNotificationStatusEnum.AVAILABLE);
        pushNotificationService.save(pushNotificationEntity);
    }
}
