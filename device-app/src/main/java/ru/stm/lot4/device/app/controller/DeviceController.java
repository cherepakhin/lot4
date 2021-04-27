package ru.stm.lot4.device.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.device.app.dto.DeviceRegistryRequest;
import ru.stm.lot4.device.app.service.DeviceService;
import ru.stm.lot4.device.app.dto.DeviceDeleteRequest;
import ru.stm.lot4.device.app.exception.EmptyResultException;
import ru.stm.lot4.device.app.exception.ValidationException;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    private ResponseEntity registryUser(@RequestBody DeviceRegistryRequest deviceRegistryRequest){
        try {
            PhoneEntity phoneEntity = deviceService.registryDevice(deviceRegistryRequest);
            log.info("Device has been registry: {}", phoneEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ValidationException e) {
            log.error("Validation exception: {}", deviceRegistryRequest,e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            log.error("Error: {}", deviceRegistryRequest, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error. Please contact to administration.");
        }
    }

    @DeleteMapping
    private ResponseEntity deleteUser(@RequestBody DeviceDeleteRequest deviceDeleteRequest){
        try {
            Integer deletedCount = deviceService.deleteDeviceByToken(deviceDeleteRequest);
            log.info("{} devices has been deleted. {}", deletedCount, deviceDeleteRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ValidationException e) {
            log.error("Validation exception: {}", deviceDeleteRequest, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (EmptyResultException e){
            log.info("Not deleted devices with firebase token: {}", deviceDeleteRequest);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            log.error("Error: {}", deviceDeleteRequest, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error. Please contact to administration.");
        }
    }
}
