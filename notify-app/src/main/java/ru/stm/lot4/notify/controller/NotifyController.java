package ru.stm.lot4.notify.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stm.lot4.dto.PushNotificationRequest;
import ru.stm.lot4.notify.service.impl.NotifyServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/receiver/pushNotification")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class NotifyController {

    private final NotifyServiceImpl notifyService;

    @PostMapping(value = "/create")
    @ApiOperation(
            code = 201,
            value = "Создание push-уведомления на указанные в теле запроса телефоны",
            notes = "Создание push-уведомления на указанные в теле запроса телефоны"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Успешный запрос на создание push-уведомления"),
            @ApiResponse(code = 400, message = "Ошибка валидации входных данных"),
            @ApiResponse(code = 500, message = "Ошибка на стороне сервера", response = String.class)
    })
    public ResponseEntity<String> create(@Valid @RequestBody PushNotificationRequest pushNotificationRequest) {
        String uuid = notifyService.sendRequest(pushNotificationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(uuid);
    }
}
