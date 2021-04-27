package ru.stm.lot4.device.app.service;

import ru.stm.lot4.model.PhoneEntity;

public interface PhoneService {
    PhoneEntity save(PhoneEntity phoneEntity);
    Integer deleteByToken(String token);
}
