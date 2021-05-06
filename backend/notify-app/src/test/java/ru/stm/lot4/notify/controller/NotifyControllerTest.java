package ru.stm.lot4.notify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.stm.lot4.dto.PushNotificationRequest;
import ru.stm.lot4.notify.controller.NotifyController;
import ru.stm.lot4.notify.service.impl.NotifyServiceImpl;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotifyController.class)
class NotifyControllerTest {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NotifyServiceImpl notifyService;

    @Test
    void createNotValid() throws Exception {
        PushNotificationRequest requestDto = new PushNotificationRequest();
        this.mockMvc.perform(post("/receiver/pushNotification/create")
                .contentType("application/json")
                .content(mapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void create() throws Exception {
        String uuid = UUID.randomUUID().toString();
        when(notifyService.sendRequest(any())).thenReturn(uuid);
        PushNotificationRequest requestDto = new PushNotificationRequest();
        requestDto.setBody("BODY");
        requestDto.setDate(new Date());
        requestDto.setTitle("TITLE");
        requestDto.setPhones(Collections.singleton("+79999999999"));
        MvcResult mvcResult = this.mockMvc.perform(post("/receiver/pushNotification/create")
                .contentType("application/json")
                .content(mapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals(uuid, mvcResult.getResponse().getContentAsString());
        verify(notifyService, times(1)).sendRequest(requestDto);
    }
}