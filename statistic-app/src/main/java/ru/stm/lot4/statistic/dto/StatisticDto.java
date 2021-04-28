package ru.stm.lot4.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StatisticDto {
    private String version;
    private Long registrationsCount;
    private Long phoneNumbersCount;
}
