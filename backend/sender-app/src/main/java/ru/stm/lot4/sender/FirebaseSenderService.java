package ru.stm.lot4.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.sender.firebasemessage.FireBaseMessage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
public class FirebaseSenderService {

    private final String urlFCM;
    String fcmServiceAccountKey;
    ObjectMapper mapper = new ObjectMapper();
    ExecutorService executors;
    RestTemplate restTemplate = new RestTemplate();
    private IFirebaseChecker checker;


    public FirebaseSenderService(String fcmServiceAccountKey,
                                 ExecutorService executors,
                                 String urlFCM) {
        this.fcmServiceAccountKey = fcmServiceAccountKey;
        this.executors = executors;
        this.urlFCM = urlFCM;
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
        try {
            ResponseEntity<String> response = getRestTemplate().postForEntity(urlFCM, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                pause(response.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            pause(e.getMessage());
        }
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @SneakyThrows
    String getBody(PushNotificationDto dto) {
        return mapper.writeValueAsString(new FireBaseMessage(dto));
    }

    public void setFirebaseChecker(IFirebaseChecker checker) {
        this.checker = checker;
    }

    void pause(String cause) {
        if (checker != null) {
            checker.pause(cause);
        }
    }
}
