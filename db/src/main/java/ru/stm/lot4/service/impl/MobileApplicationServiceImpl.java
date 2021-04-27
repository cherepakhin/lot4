package ru.stm.lot4.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.stm.lot4.model.MobileApplicationEntity;
import ru.stm.lot4.repository.MobileApplicationRepository;
import ru.stm.lot4.service.MobileApplicationService;

@Service
@RequiredArgsConstructor
public class MobileApplicationServiceImpl implements MobileApplicationService {

    private final MobileApplicationRepository mobileApplicationRepository;

    @Override
    @Cacheable("mobileApplications")
    public MobileApplicationEntity findByVersion(String version) {
        return mobileApplicationRepository.findByVersion(version);
    }
}
