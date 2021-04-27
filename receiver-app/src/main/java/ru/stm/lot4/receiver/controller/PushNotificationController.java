package ru.stm.lot4.receiver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.receiver.service.PushNotificationService;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.receiver.mapper.PushNotificationMapper;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/front/pushNotification")
@RequiredArgsConstructor
@Slf4j
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;
    private final PushNotificationMapper mapper;

    @PostMapping(value = "/create")
    public ResponseEntity<PushNotificationDto> create(@Valid @RequestBody PushNotificationDto pushNotificationDto) {
        PushNotificationEntity pushNotificationEntity = pushNotificationService
                .save(mapper.fromDTO(pushNotificationDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toDTO(pushNotificationEntity));
    }

    @GetMapping(value = "/pageable")
    public ResponseEntity<List<PushNotificationDto>> getPageable(
            @PageableDefault(size = 100, sort = "date") Pageable page) {
        List<PushNotificationDto> pushNotificationDtoList = pushNotificationService.findAll(page)
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(pushNotificationDtoList);
    }

}
