package controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import dto.StatisticResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.StatisticService;

@RestController
@RequestMapping("/statistic")
public class StatisticServiceController {

    private static final String PHONE_REGEX = "^((\\+7|7|8)+([0-9]){10})$";

    private final StatisticService statisticService;

    public StatisticServiceController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    public StatisticResponseDto getApplicationsStatistic() {
        statisticService.getApplicationStat();
        return new StatisticResponseDto();
    }

    public StatisticResponseDto getMessagesByPhone(@RequestParam @JsonFormat(pattern = PHONE_REGEX) String phone) {
        statisticService.getMessagesByPhone(phone);
        return new StatisticResponseDto();
    }
}
