package ru.stm.lot4.statistic.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.stm.lot4.statistic.dto.MessageDto;
import ru.stm.lot4.statistic.dto.StatisticDto;
import ru.stm.lot4.statistic.service.StatisticService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/statistic")
@Slf4j
@Validated
public class StatisticController {

    private static final String PHONE_REGEX = "^((\\+7|7|8|)+([0-9]){10})$";

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/application")
    @CrossOrigin
    @ApiOperation(
            value = "Отображение списка всех зарегистрированных в системе версий мобильного приложения, " +
                    "с указанием количества регистраций и количества уникальных номеров телефонов для каждой версии",
            notes = "Отображение списка всех зарегистрированных в системе версий мобильного приложения, " +
                    "с указанием количества регистраций и количества уникальных номеров телефонов для каждой версии"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Успешный запрос на получение статистики"),
            @ApiResponse(code = 500, message = "Ошибка на стороне сервера", response = String.class)
    })
    public ResponseEntity<List<StatisticDto>> getApplicationsStatistic() {
        return ResponseEntity.ok(statisticService.getApplicationStat());
    }

    @GetMapping("/messages")
    @CrossOrigin
    @ApiOperation(
            code = 200,
            value = "Отображения списка (с пейджингом) всех сообщений, ранее отправленных на указанный номер телефона",
            notes = "Отображения списка (с пейджингом) всех сообщений, ранее отправленных на указанный номер телефона"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Успешный запрос на получение отправленных сообщений по указанному номеру"),
            @ApiResponse(code = 400, message = "Ошибка валидации входных данных"),
            @ApiResponse(code = 500, message = "Ошибка на стороне сервера")
    })
    public ResponseEntity<List<MessageDto>> getMessagesByPhone(@RequestParam("phone")
                                                               @Pattern(regexp = PHONE_REGEX, message = "Incorrect phone number")
                                                               @NotBlank(message = "Phone number can't be empty") String phone,
                                                               @PageableDefault(size = Integer.MAX_VALUE) Pageable page) {
        return ResponseEntity.ok(statisticService.getMessagesByPhone(phone, page));
    }
}
