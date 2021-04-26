package ru.stm.lo4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStatisticEntity {
    String appId;
    String appKey;
    Long countOfRegistration;
    Long countOfUniquePhones;
}
