package ru.stm.lo4.service;

import ru.stm.lo4.model.PhoneEntity;

public interface PhoneService {
    PhoneEntity save(PhoneEntity phoneEntity);
    Integer deleteByToken(String token);
}
