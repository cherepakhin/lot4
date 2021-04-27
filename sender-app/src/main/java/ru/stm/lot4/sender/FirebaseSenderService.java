package ru.stm.lot4.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.sender.firebasemessage.FireBaseMessage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
public class FirebaseSenderService {

    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    String fcmServiceAccountKey;
    ObjectMapper mapper = new ObjectMapper();
    ExecutorService executors;
    RestTemplate restTemplate = new RestTemplate();


    public FirebaseSenderService(String fcmServiceAccountKey, ExecutorService executors) {
        this.fcmServiceAccountKey = fcmServiceAccountKey;
        this.executors=executors;
    }

    @SneakyThrows
    public void sendAsync(PushNotificationDto dto) {
        CompletableFuture.runAsync(() -> send(dto), executors).get();
    }

    public void send(PushNotificationDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "key=" + fcmServiceAccountKey);
        HttpEntity<String> request =
                new HttpEntity<>(getBody(dto), headers);
        log.info(request.toString());
        String response = getRestTemplate().postForObject(FIREBASE_API_URL, request, String.class);
        log.info(response);
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @SneakyThrows
    String getBody(PushNotificationDto dto) {
        return mapper.writeValueAsString(new FireBaseMessage(dto));
    }
}
