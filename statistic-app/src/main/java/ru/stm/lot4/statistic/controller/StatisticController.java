package ru.stm.lot4.statistic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.stm.lot4.model.MobileApplicationStatisticEntity;
import ru.stm.lot4.statistic.dto.MessageDto;
import ru.stm.lot4.statistic.service.StatisticService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/statistic")
@Slf4j
@Validated
public class StatisticController {

    private static final String PHONE_REGEX = "^\\+?(\\d[\\d-. ]+)?(\\([\\d-. ]+\\))?[\\d-. ]+\\d$";

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/application")
    public ResponseEntity<List<MobileApplicationStatisticEntity>> getApplicationsStatistic() {
        return ResponseEntity.ok(statisticService.getApplicationStat());
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getMessagesByPhone(@RequestParam("phone")
                                                               @Pattern(regexp = PHONE_REGEX, message = "Incorrect phone number")
                                                               @NotBlank(message = "Phone number can't be empty") String phone,
                                                               @PageableDefault(size = Integer.MAX_VALUE) Pageable page) {
        return ResponseEntity.ok(statisticService.getMessagesByPhone(phone, page));
    }
}
