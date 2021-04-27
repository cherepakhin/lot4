package ru.stm.lot4.device.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.stm.lot4.model.MobileApplicationEntity;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.service.MobileApplicationService;
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

    private final ru.stm.lot4.service.PhoneService phoneService;
    private final MobileApplicationService mobileApplicationService;

    @Override
    @Transactional
    public PhoneEntity registryDevice(DeviceRegistryRequest deviceRegistryRequest) throws ValidationException {

        String number = deviceRegistryRequest.getNumber();
        String version = deviceRegistryRequest.getVersion();
        String token = deviceRegistryRequest.getFirebaseToken();

        if (Objects.isNull(token)){
            throw new ValidationException("Firebase token is empty.");
        }

        if (Objects.isNull(number)){
            throw new ValidationException("Number is empty.");
        }

        if (Objects.isNull(version)){
            throw new ValidationException("Version is empty.");
        }

        number = StringUtils.getDigits(number);

        if (number.length() != 11
                || (!number.startsWith("7")
                && !number.startsWith("8"))){
            throw new ValidationException("Not valid number. Please take in format +7xxxxxxxxxx or 8xxxxxxxxxx");
        }

        MobileApplicationEntity mobileApplication = mobileApplicationService.findByVersion(version);

        if (Objects.isNull(mobileApplication)){
            throw new ValidationException("Not valid version.");
        }


        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setNumber(number);
        phoneEntity.setApp(mobileApplication);
        phoneEntity.setToken(token);
        return phoneService.save(phoneEntity);
    }

    @Override
    @Transactional
    public Integer deleteDeviceByToken(DeviceDeleteRequest deviceDeleteRequest) throws ValidationException, EmptyResultException {
        String token = deviceDeleteRequest.getFirebaseToken();

        if(Objects.isNull(token)){
            throw new ValidationException("Firebase token is empty.");
        }

        Integer deletedCount = phoneService.deleteByToken(token);
        if (deletedCount == 0){
            throw new EmptyResultException("Not found rows with firebase token.");
        }
        return deletedCount;
    }
}
