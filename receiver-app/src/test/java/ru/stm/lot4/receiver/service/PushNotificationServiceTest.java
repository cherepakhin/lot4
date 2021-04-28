package ru.stm.lot4.receiver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import ru.stm.lot4.model.MobileApplicationEntity;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;
import ru.stm.lot4.receiver.mapper.PushNotificationMapper;
import ru.stm.lot4.receiver.service.impl.PushNotificationServiceImpl;
import ru.stm.lot4.repository.PhoneRepository;
import ru.stm.lot4.repository.PushNotificationRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class PushNotificationServiceTest {
    private PushNotificationService pushNotificationService;

    private PushNotificationRepository pushNotificationRepository = mock(PushNotificationRepository.class);
    private PhoneRepository phoneRepository = mock(PhoneRepository.class);
    private PushNotificationMapper mapper = Mappers.getMapper(PushNotificationMapper.class);

    @BeforeEach
    void initService() {
        pushNotificationService = new PushNotificationServiceImpl(pushNotificationRepository, phoneRepository, mapper);
    }

    @Test
    void test_save() {
        MobileApplicationEntity mobileApplication = new MobileApplicationEntity();
        mobileApplication.setVersion("1.0");
        PhoneEntity phoneDto = new PhoneEntity();
        phoneDto.setToken("token");
        phoneDto.setApp(mobileApplication);
        phoneDto.setNumber("+79999999999");
        PushNotificationEntity pushNotification = new PushNotificationEntity();
        pushNotification.setBody("body");
        pushNotification.setDate(new Date());
        pushNotification.setPhones(Collections.singleton(phoneDto));
        pushNotification.setTitle("title");
        pushNotificationService.save(pushNotification);
        verify(pushNotificationRepository, times(1))
                .save(eq(pushNotification));
    }

    @Test
    void test_save_all() {
        MobileApplicationEntity mobileApplication = new MobileApplicationEntity();
        mobileApplication.setVersion("1.0");
        PhoneEntity phoneDto = new PhoneEntity();
        phoneDto.setToken("token");
        phoneDto.setApp(mobileApplication);
        phoneDto.setNumber("+79999999999");
        PushNotificationEntity pushNotification = new PushNotificationEntity();
        pushNotification.setBody("body");
        pushNotification.setDate(new Date());
        pushNotification.setPhones(Collections.singleton(phoneDto));
        pushNotification.setTitle("title");
        List<PushNotificationEntity> pushNotificationEntityList = Collections.singletonList(pushNotification);
        pushNotificationService.saveAll(pushNotificationEntityList);
        verify(pushNotificationRepository)
                .saveAll(pushNotificationEntityList);
    }

    @Test
    void test_find_all() {
        pushNotificationService.findAll(Pageable.unpaged());
        verify(pushNotificationRepository, times(1))
                .findAll(Pageable.unpaged());
    }

    @Test
    void test_receive_available() {
        pushNotificationService.receiveActualAvailablePushNotification();
        verify(pushNotificationRepository, times(1))
                .findAllByDateBeforeAndStatus(any(), eq(PushNotificationStatusEnum.AVAILABLE));
    }

    @Test
    void test_find_all_by_date_and_status() {
        Date date = new Date();
        pushNotificationService.findAllByDateBeforeAndStatus(date, PushNotificationStatusEnum.AVAILABLE);
        verify(pushNotificationRepository, times(1))
                .findAllByDateBeforeAndStatus(date, PushNotificationStatusEnum.AVAILABLE);
    }


}
