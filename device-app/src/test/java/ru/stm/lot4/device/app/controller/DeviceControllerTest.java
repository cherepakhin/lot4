package ru.stm.lot4.device.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.stm.lot4.device.app.dto.DeviceDeleteRequest;
import ru.stm.lot4.device.app.dto.DeviceRegistryRequest;
import ru.stm.lot4.device.app.service.DeviceService;
import ru.stm.lot4.device.app.service.MobileApplicationService;

import javax.persistence.EntityManagerFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeviceService deviceService;
    @MockBean
    private MobileApplicationService mobileApplicationService;
    @MockBean
    private EntityManagerFactory entityManagerFactory;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void emptyFirebaseTokenCaseRegistryDevice() throws Exception {
        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setFirebaseToken("");
        deviceRegistryRequest.setVersion("ver 0.1");
        deviceRegistryRequest.setNumber("89999999999");

        this.mockMvc.perform(post("/device")
                .contentType("application/json")
        .content(objectMapper.writeValueAsString(deviceRegistryRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void nullFirebaseTokenCaseRegistryDevice() throws Exception {
        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setFirebaseToken(null);
        deviceRegistryRequest.setVersion("ver 0.1");
        deviceRegistryRequest.setNumber("89999999999");

        this.mockMvc.perform(post("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceRegistryRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void emptyVersionCaseRegistryDevice() throws Exception {
        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setFirebaseToken("token");
        deviceRegistryRequest.setVersion("");
        deviceRegistryRequest.setNumber("89999999999");

        this.mockMvc.perform(post("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceRegistryRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void nullVersionCaseRegistryDevice() throws Exception {
        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setFirebaseToken("token");
        deviceRegistryRequest.setVersion(null);
        deviceRegistryRequest.setNumber("89999999999");

        this.mockMvc.perform(post("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceRegistryRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void emptyNumberCaseRegistryDevice() throws Exception {
        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setFirebaseToken("token");
        deviceRegistryRequest.setVersion("ver 0.1");
        deviceRegistryRequest.setNumber("");

        this.mockMvc.perform(post("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceRegistryRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void nullNumberCaseRegistryDevice() throws Exception {
        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setFirebaseToken("token");
        deviceRegistryRequest.setVersion("ver 0.1");
        deviceRegistryRequest.setNumber(null);

        this.mockMvc.perform(post("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceRegistryRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "xxxx",
            "+49841512xc31564132",
            "645ac646a",
            "+51sd31546z1113",
            "l904-512-546-456",
            "69999999999",
            "+19999464819",
            "+48841356432",
            "+7-949-546-12-45"
    })
    void invalidNumberCaseRegistryDevice(String number) throws Exception {
        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setFirebaseToken("token");
        deviceRegistryRequest.setVersion("ver 0.1");
        deviceRegistryRequest.setNumber(number);

        this.mockMvc.perform(post("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceRegistryRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "+79999999999",
            "89999999999",
            "79999999999",
            "85641231561",
            "+79513254565",
            "86451235481",
            "78941236582"
    })
    void validNumberCaseRegistryDevice(String number) throws Exception {
        DeviceRegistryRequest deviceRegistryRequest = new DeviceRegistryRequest();
        deviceRegistryRequest.setFirebaseToken("token");
        deviceRegistryRequest.setVersion("ver 0.1");
        deviceRegistryRequest.setNumber(number);

        this.mockMvc.perform(post("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceRegistryRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "+toke",
            "jkda11",
            "kdsajkldjlk3jlkjkldsmcz",
            "0asdajklkcz",
            "+-/",
            "121hlklzlzlz894u9-ajsk",
            "1"
    })
    void validTokenCaseDeleteUser(String token) throws Exception {
        DeviceDeleteRequest deviceDeleteRequest = new DeviceDeleteRequest();
        deviceDeleteRequest.setFirebaseToken(token);

        this.mockMvc.perform(delete("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceDeleteRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void emptyTokenCaseDeleteUser() throws Exception {
        DeviceDeleteRequest deviceDeleteRequest = new DeviceDeleteRequest();
        deviceDeleteRequest.setFirebaseToken("");

        this.mockMvc.perform(delete("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceDeleteRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void nullTokenCaseDeleteUser() throws Exception {
        DeviceDeleteRequest deviceDeleteRequest = new DeviceDeleteRequest();
        deviceDeleteRequest.setFirebaseToken(null);

        this.mockMvc.perform(delete("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(deviceDeleteRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void nullRequestCaseDeleteUser() throws Exception {
        this.mockMvc.perform(delete("/device")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(null)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}