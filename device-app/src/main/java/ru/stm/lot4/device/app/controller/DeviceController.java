package ru.stm.lot4.device.app.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stm.lot4.device.app.dto.DeviceDeleteRequest;
import ru.stm.lot4.device.app.dto.DeviceRegistryRequest;
import ru.stm.lot4.device.app.exception.EmptyResultException;
import ru.stm.lot4.device.app.exception.ValidationException;
import ru.stm.lot4.device.app.service.DeviceService;
import ru.stm.lot4.model.PhoneEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/device")
@Slf4j
public class DeviceController {

    private DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    @ApiOperation(
            code = 201,
            value = "Регистрация нового девайса в системе.",
            notes = "Регистрация нового девайса в системе."
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Девайс зарегестрирован."),
            @ApiResponse(code = 400, message = "Ошибка валидации входных данных.", response = String.class),
            @ApiResponse(code = 500, message = "Ошибка на стороне сервера", response = String.class),
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    private ResponseEntity<String> registryUser(
            @RequestBody
            @Valid
            @ApiParam(name = "Device Info", required = true, value = "Информация о девайсе.")
            DeviceRegistryRequest deviceRegistryRequest
    ){
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

    @ApiOperation(
            code = 200,
            value = "Удаление девайса по Firebase Token",
            notes = "Удаление девайса по Firebase Token"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Девайс удален."),
            @ApiResponse(code = 400, message = "Ошибка валидации входных данных.", response = String.class),
            @ApiResponse(code = 500, message = "Ошибка на стороне сервера", response = String.class),
    })
    @DeleteMapping
    private ResponseEntity<String> deleteUser(
            @RequestBody
            @Valid
            @ApiParam(name = "Firebase Token", required = true, value = "Токен для удаления девайса.") DeviceDeleteRequest deviceDeleteRequest){
        try {
            Integer deletedCount = deviceService.deleteDeviceByToken(deviceDeleteRequest);
            log.info("{} devices has been deleted. {}", deletedCount, deviceDeleteRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EmptyResultException e){
            log.info("Not deleted devices with firebase token: {}", deviceDeleteRequest);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            log.error("Error: {}", deviceDeleteRequest, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error. Please contact to administration.");
        }
    }
}
