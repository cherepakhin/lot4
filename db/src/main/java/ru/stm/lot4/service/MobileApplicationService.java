package ru.stm.lot4.service;

import ru.stm.lot4.model.MobileApplicationEntity;

public interface MobileApplicationService {
    MobileApplicationEntity findByVersion(String version);
}
