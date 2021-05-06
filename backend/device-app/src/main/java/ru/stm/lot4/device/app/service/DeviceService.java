package ru.stm.lot4.device.app.service;

import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.device.app.dto.DeviceDeleteRequest;
import ru.stm.lot4.device.app.dto.DeviceRegistryRequest;
import ru.stm.lot4.device.app.exception.EmptyResultException;
import ru.stm.lot4.device.app.exception.ValidationException;

public interface DeviceService {
    PhoneEntity registryDevice(DeviceRegistryRequest deviceRegistryRequest) throws ValidationException;
    Integer deleteDeviceByToken(DeviceDeleteRequest deviceDeleteRequest) throws ValidationException, EmptyResultException;
}
