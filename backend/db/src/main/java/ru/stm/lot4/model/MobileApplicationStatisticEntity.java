package ru.stm.lot4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobileApplicationStatisticEntity implements Serializable {
    private String appVersion;
    private Long registrationsCount;
    private Long phoneNumbersCount;
}
