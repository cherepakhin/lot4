package ru.stm.lot4.statistic.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import ru.stm.lot4.model.PushNotificationStatusEnum;
import ru.stm.lot4.repository.PushNotificationRepository;
import ru.stm.lot4.repository.MobileApplicationRepository;
import ru.stm.lot4.statistic.service.impl.StatisticServiceImpl;
import ru.stm.lot4.statistic.service.mapper.MobileApplicationStatisticEntityMapper;
import ru.stm.lot4.statistic.service.mapper.PushNotificationEntityMapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class StatisticServiceTest {
    StatisticService statisticService;
    MobileApplicationRepository applicationRepository = mock(MobileApplicationRepository.class);
    PushNotificationEntityMapper notificationEntityMapper = mock(PushNotificationEntityMapper.class);
    MobileApplicationStatisticEntityMapper statisticEntityMapper = mock(MobileApplicationStatisticEntityMapper.class);
    PushNotificationRepository pushNotificationRepository = mock(PushNotificationRepository.class);

    @BeforeEach
    void setUp() {
        statisticService = new StatisticServiceImpl(applicationRepository, statisticEntityMapper,
                                                    notificationEntityMapper, pushNotificationRepository);
    }

    @Test
    void getApplicationStatisticTest() {
        statisticService.getApplicationStat();
        verify(applicationRepository, times(1)).getStatistic();
    }

    @Test
    void getMessagesByPhoneTest() {
        String phone = "89999999999";
        statisticService.getMessagesByPhone(phone, Pageable.unpaged());
        verify(pushNotificationRepository, times(1)).findReceivedMessagesByPhoneAndStatus(phone,
                PushNotificationStatusEnum.NOT_AVAILABLE.getCode(), Pageable.unpaged());
    }

    @AfterEach
    void tearDown() {
        statisticService = null;
    }
}
