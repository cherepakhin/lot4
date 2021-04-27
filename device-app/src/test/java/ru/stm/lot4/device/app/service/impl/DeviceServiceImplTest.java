package ru.stm.lot4.device.app.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stm.lot4.device.app.dto.DeviceDeleteRequest;
import ru.stm.lot4.device.app.dto.DeviceRegistryRequest;
import ru.stm.lot4.device.app.exception.ValidationException;
import ru.stm.lot4.device.app.service.DeviceService;
import ru.stm.lot4.model.MobileApplicationEntity;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.service.MobileApplicationService;
import ru.stm.lot4.service.PhoneService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeviceServiceImplTest {
    DeviceService deviceService;
    PhoneService phoneService = mock(PhoneService.class);
    MobileApplicationService mobileApplicationService = mock(MobileApplicationService.class);

    @BeforeEach
    void setUp() {
        deviceService = new DeviceServiceImpl(phoneService, mobileApplicationService);
    }

    @Test
    void successRegistryDevice() throws ValidationException {
        String version = "ver 0.1";
        when(mobileApplicationService.findByVersion(version)).thenReturn(new MobileApplicationEntity());
        when(phoneService.save(any(PhoneEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setNumber("8999999999");
        deviceRegistryRequest.setVersion(version);
        deviceRegistryRequest.setFirebaseToken("token");
        PhoneEntity phoneEntity = deviceService.registryDevice(deviceRegistryRequest);

        verify(phoneService, times(1)).save(phoneEntity);
    }

    @Test
    void notSuccessRegistryDevice() {
        String version = "ver 0.1";
        when(mobileApplicationService.findByVersion(version)).thenReturn(new MobileApplicationEntity());
        when(phoneService.save(any(PhoneEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setNumber("8999999999");
        deviceRegistryRequest.setVersion("agaga");
        deviceRegistryRequest.setFirebaseToken("token");

        assertThrows(ValidationException.class, () -> deviceService.registryDevice(deviceRegistryRequest));
    }

    @Test
    void successDeleteDeviceByToken() {
        String validToken = "valid token";
        when(phoneService.deleteByToken(validToken)).thenAnswer(i -> 5);

        DeviceDeleteRequest deviceDeleteRequest = new DeviceDeleteRequest();
        deviceDeleteRequest.setFirebaseToken(validToken);
        assertEquals(phoneService.deleteByToken(deviceDeleteRequest.getFirebaseToken()), 5);
    }

    @Test
    void notSuccessDeleteDeviceByToken() {
        String validToken = "valid token";
        when(phoneService.deleteByToken("invalid token")).thenAnswer(i -> 5);

        DeviceDeleteRequest deviceDeleteRequest = new DeviceDeleteRequest();
        deviceDeleteRequest.setFirebaseToken(validToken);
        assertEquals(phoneService.deleteByToken(deviceDeleteRequest.getFirebaseToken()), 0);
    }
}