package ru.stm.lo4.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stm.lo4.model.PhoneEntity;
import ru.stm.lo4.repository.PhoneRepository;
import ru.stm.lo4.service.PhoneService;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;

    @Override
    public PhoneEntity save(PhoneEntity phoneEntity) {
        return phoneRepository.save(phoneEntity);
    }
}
