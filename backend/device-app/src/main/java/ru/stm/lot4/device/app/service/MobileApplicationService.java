package ru.stm.lot4.device.app.service;

import ru.stm.lot4.model.MobileApplicationEntity;

public interface MobileApplicationService {
    MobileApplicationEntity findByVersion(String version);
}
