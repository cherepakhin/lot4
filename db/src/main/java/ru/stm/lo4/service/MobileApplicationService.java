package ru.stm.lo4.service;

import ru.stm.lo4.model.MobileApplicationEntity;

public interface MobileApplicationService {
    MobileApplicationEntity findByVersion(String version);
}
