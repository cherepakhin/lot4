package ru.stm.lot4.device.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stm.lot4.device.app.service.PhoneService;
import ru.stm.lot4.model.MobileApplicationEntity;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.device.app.service.MobileApplicationService;
import ru.stm.lot4.device.app.dto.DeviceRegistryRequest;
import ru.stm.lot4.device.app.service.DeviceService;
import ru.stm.lot4.device.app.dto.DeviceDeleteRequest;
import ru.stm.lot4.device.app.exception.EmptyResultException;
import ru.stm.lot4.device.app.exception.ValidationException;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final PhoneService phoneService;
    private final MobileApplicationService mobileApplicationService;

    @Override
    @Transactional
    public PhoneEntity registryDevice(DeviceRegistryRequest deviceRegistryRequest) throws ValidationException {

        String number = deviceRegistryRequest.getNumber();
        String version = deviceRegistryRequest.getVersion();
        String token = deviceRegistryRequest.getFirebaseToken();

        MobileApplicationEntity mobileApplication = mobileApplicationService.findByVersion(version);
        if (Objects.isNull(mobileApplication)){
            throw new ValidationException("Not valid version!");
        }

        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setNumber(number);
        phoneEntity.setApp(mobileApplication);
        phoneEntity.setToken(token);
        return phoneService.save(phoneEntity);
    }

    @Override
    @Transactional
    public Integer deleteDeviceByToken(DeviceDeleteRequest deviceDeleteRequest) throws EmptyResultException {
        String token = deviceDeleteRequest.getFirebaseToken();

        Integer deletedCount = phoneService.deleteByToken(token);
        if (deletedCount == 0){
            throw new EmptyResultException("Not found rows with firebase token.");
        }
        return deletedCount;
    }
}
