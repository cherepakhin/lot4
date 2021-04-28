package ru.stm.lot4.sender;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import ru.stm.lot4.dto.PushNotificationDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirebaseCheckerTest {

    KafkaMessageListenerContainer<String, PushNotificationDto> container =
            mock(KafkaMessageListenerContainer.class);

    @Test
    void pause() {
        FirebaseChecker checker = spy(new FirebaseChecker(container, "FAKE_URL"));
        checker.check();
        verify(checker,times(1)).pause(anyString());
    }

    @Test
    void resume() {
        FirebaseChecker checker = spy(new FirebaseChecker(container, "ya.ru"));
        when(checker.isOkFirebase()).thenReturn(true);
        checker.check();
        verify(checker,times(1)).resume();
    }

    @Test
    void isOkFirebase() {
        FirebaseChecker checker = new FirebaseChecker(container, "https://fcm.googleapis.com/fcm/send");
        assertTrue(checker.isOkFirebase());
        checker = new FirebaseChecker(container, "http://FAKE_URL");
        assertFalse(checker.isOkFirebase());
    }
}