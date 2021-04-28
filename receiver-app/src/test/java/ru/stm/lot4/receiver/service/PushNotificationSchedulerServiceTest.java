package ru.stm.lot4.receiver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.stm.lot4.model.MobileApplicationEntity;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.receiver.mapper.PushNotificationMapper;
import ru.stm.lot4.receiver.service.impl.PushNotificationSchedulerServiceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PushNotificationSchedulerServiceTest {

    private PushNotificationService pushNotificationService = mock(PushNotificationService.class);
    private PushNotificationSenderService pushNotificationSenderService = spy(PushNotificationSenderService.class);
    private PushNotificationSchedulerService pushNotificationSchedulerService;
    private PushNotificationMapper mapper;
    private PushNotificationEntity pushNotification;

    @BeforeEach
    void initService() {
        this.mapper = Mappers.getMapper(PushNotificationMapper.class);
        MobileApplicationEntity mobileApplication = new MobileApplicationEntity();
        mobileApplication.setVersion("1.0");
        PhoneEntity phoneDto = new PhoneEntity();
        phoneDto.setToken("token");
        phoneDto.setApp(mobileApplication);
        phoneDto.setNumber("+79999999999");
        this.pushNotification = new PushNotificationEntity();
        pushNotification.setBody("body");
        pushNotification.setDate(new Date());
        pushNotification.setPhones(Collections.singleton(phoneDto));
        pushNotification.setTitle("title");
        List<PushNotificationEntity> pushNotificationEntities = Collections.singletonList(pushNotification);
        when(pushNotificationService.receiveActualAvailablePushNotification()).thenReturn(pushNotificationEntities);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        pushNotificationSchedulerService = new PushNotificationSchedulerServiceImpl(
                pushNotificationService,
                scheduledThreadPoolExecutor,
                mapper,
                pushNotificationSenderService);
    }

    @Test
    void test_scheduled_receive_data() throws JsonProcessingException, InterruptedException {
        Thread.sleep(3000);
        verify(pushNotificationSenderService, atLeast(2)).send(eq(mapper.toDTO(pushNotification)), any());
    }
}
