package ru.stm.lot4.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.stm.lot4.dto.PhoneDto;
import ru.stm.lot4.dto.PushNotificationDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class FirebaseSenderServiceTest {

    private MockRestServiceServer mockServer;

    private RestTemplate restTemplate = new RestTemplate();
    private ExecutorService executors = Executors.newFixedThreadPool(1);

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getBody() throws JsonProcessingException {
        FirebaseSenderService service = new FirebaseSenderService("SERVER_KEY", executors,"");
        PushNotificationDto dto = new PushNotificationDto();
        dto.setTitle("TITLE");
        dto.setBody("BODY");

        PhoneDto phoneDto1 = new PhoneDto();
        phoneDto1.setToken("TOKEN1");
        dto.getPhones().add(phoneDto1);

        PhoneDto phoneDto2 = new PhoneDto();
        phoneDto2.setToken("TOKEN2");
        dto.getPhones().add(phoneDto2);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(dto));
        String jsonDto = service.getBody(dto);
        System.out.println(jsonDto);
        assertEquals("{\"notification\":{\"title\":\"TITLE\",\"body\":\"BODY\"},\"data\":\"\",\"priority\":\"high\",\"direct_boot_ok\":true,\"registration_ids\":[\"TOKEN2\",\"TOKEN1\"]}",
                jsonDto);
    }

    @Test
    void sendToFirebase() throws URISyntaxException {
        FirebaseSenderService service =
                new FirebaseSenderService("FCM_SERVICE_ACCOUNT_KEY", executors,"https://fcm.googleapis.com/fcm/send");
        FirebaseSenderService spyService = spy(service);
        when(spyService.getRestTemplate()).thenReturn(restTemplate);

        PushNotificationDto dto = new PushNotificationDto();
        dto.setTitle("TITLE");
        dto.setBody("BODY");

        PhoneDto phoneDto1 = new PhoneDto();
        phoneDto1.setToken("TOKEN1");
        dto.getPhones().add(phoneDto1);

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("https://fcm.googleapis.com/fcm/send")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "key=FCM_SERVICE_ACCOUNT_KEY"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"notification\":{\"title\":\"TITLE\",\"body\":\"BODY\"},\"data\":\"\",\"priority\":\"high\",\"registration_ids\":[\"TOKEN1\"]}"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("Ok")
                );

        spyService.send(dto);
        mockServer.verify();
    }

    @Test
    void pauseOnError() {
        FirebaseChecker firebaseChecker = mock(FirebaseChecker.class);
        FirebaseSenderService service =
                new FirebaseSenderService("FCM_SERVICE_ACCOUNT_KEY",
                        executors,
                        "http://FAKE_URL");
        service.setFirebaseChecker(firebaseChecker);
        PushNotificationDto dto = new PushNotificationDto();
        dto.setTitle("TITLE");
        dto.setBody("BODY");

        PhoneDto phoneDto1 = new PhoneDto();
        phoneDto1.setToken("TOKEN1");
        dto.getPhones().add(phoneDto1);
        service.send(dto);
        verify(firebaseChecker, times(1)).pause(anyString());
    }
}