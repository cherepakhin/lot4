package ru.stm.lot4.receiver.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.receiver.mapper.PushNotificationMapper;
import ru.stm.lot4.receiver.service.PushNotificationService;

import java.util.List;

@RestController
@RequestMapping(value = "/receiver/pushNotification")
@RequiredArgsConstructor
@Slf4j
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;
    private final PushNotificationMapper mapper;

    @GetMapping(value = "/pageable")
    @ApiOperation(
            code = 200,
            value = "Отображения списка (с пейджингом) всех push-уведомлений",
            notes = "Отображения списка (с пейджингом) всех push-уведомлений"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Успешный запрос на получение push-уведомлений"),
            @ApiResponse(code = 500, message = "Ошибка на стороне сервера")
    })
    public ResponseEntity<List<PushNotificationDto>> getPageable(
            @PageableDefault(size = 100, sort = "date") Pageable page) {
        List<PushNotificationDto> pushNotificationDtoList = pushNotificationService.findAll(page)
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(pushNotificationDtoList);
    }

}
