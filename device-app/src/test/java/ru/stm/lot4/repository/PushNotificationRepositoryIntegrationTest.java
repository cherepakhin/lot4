package ru.stm.lot4.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import ru.stm.lot4.device.app.configuration.ComponentScanner;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ComponentScanner.class)
@Sql("/fixtures/app_phone.sql")
//@Transactional
public class PushNotificationRepositoryIntegrationTest {

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    PushNotificationRepository pushNotificationRepository;

    @Test
    void findById() {
        Optional<PhoneEntity> phone = phoneRepository.findById(1L);
        assertTrue(phone.isPresent());
    }

    @Test
    void createNotification() {
        PhoneEntity phone1 = phoneRepository.findById(1L).get();
        PhoneEntity phone2 = phoneRepository.findById(2L).get();
        PushNotificationEntity notification = new PushNotificationEntity("1",
                "TITLE","BODY",new Date(), PushNotificationStatusEnum.AVAILABLE);
        notification.getPhones().add(phone1);
        notification.getPhones().add(phone2);
        PushNotificationEntity saved = pushNotificationRepository.save(notification);
        assertNotNull(saved.getId());

        PushNotificationEntity found = pushNotificationRepository.findById("1").get();
        assertEquals("TITLE", found.getTitle());
        assertEquals("BODY", found.getBody());
        assertEquals(2, found.getPhones().size());
    }

    @Test
    void findAllByDateBeforeAndStatus() {
        Calendar cal = Calendar.getInstance();
        cal.set(2021,4,30);
        PhoneEntity phone1 = phoneRepository.findById(1L).get();
        PushNotificationEntity notification1 = new PushNotificationEntity("1",
                "","",cal.getTime(), PushNotificationStatusEnum.AVAILABLE);
        notification1.getPhones().add(phone1);
        pushNotificationRepository.save(notification1);

        cal.set(2021,4,20);
        PushNotificationEntity notification = new PushNotificationEntity("2",
                "","",cal.getTime(), PushNotificationStatusEnum.AVAILABLE);
        notification.getPhones().add(phone1);
        pushNotificationRepository.save(notification);

        List<PushNotificationEntity> found = pushNotificationRepository.findAllByDateBeforeAndStatus(cal.getTime(),
                PushNotificationStatusEnum.AVAILABLE,
                PageRequest.of(0,100));
        assertEquals(1, found.size());
    }
}