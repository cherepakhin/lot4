package ru.stm.lot4.receiver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stm.lot4.dto.PushNotificationDto;

@RestController
@RequestMapping(value = "/front/pushNotification")
public class PushNotificationController {

    @PostMapping(value = "/create")
    public ResponseEntity create(@RequestBody PushNotificationDto pushNotificationDto) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
