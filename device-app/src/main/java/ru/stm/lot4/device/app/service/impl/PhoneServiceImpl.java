package ru.stm.lot4.device.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.repository.PhoneRepository;
import ru.stm.lot4.device.app.service.PhoneService;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;

    @Override
    public PhoneEntity save(PhoneEntity phoneEntity) {
        return phoneRepository.save(phoneEntity);
    }

    @Override
    public Integer deleteByToken(String token) {
        return phoneRepository.deleteByToken(token);
    }
}
