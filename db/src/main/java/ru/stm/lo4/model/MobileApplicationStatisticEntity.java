package ru.stm.lo4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobileApplicationStatisticEntity {
    private String appVersion;
    private Long registrationsCount;
    private Long phoneNumbersCount;
}
