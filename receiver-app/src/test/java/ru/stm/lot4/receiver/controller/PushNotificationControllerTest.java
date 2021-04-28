package ru.stm.lot4.receiver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.stm.lot4.dto.MobileApplicationDto;
import ru.stm.lot4.dto.PhoneDto;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.model.MobileApplicationEntity;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.receiver.Application;
import ru.stm.lot4.receiver.controller.PushNotificationController;
import ru.stm.lot4.receiver.dto.PushNotificationRequest;
import ru.stm.lot4.receiver.mapper.PushNotificationMapper;
import ru.stm.lot4.receiver.service.PushNotificationService;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@WebMvcTest(PushNotificationController.class)
public class PushNotificationControllerTest {
    private static final String ROOT_PATH = "/front/pushNotification/";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EntityManagerFactory entityManagerFactory;
    @MockBean
    private PushNotificationService pushNotificationService;
    @MockBean
    private PushNotificationMapper mapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test_create_method() throws Exception {
        PushNotificationRequest pushNotificationDto = new PushNotificationRequest()
                .setBody("body")
                .setDate(new Date())
                .setPhones(Collections.singleton("+79999999999"))
                .setTitle("title");
        mockMvc.perform(MockMvcRequestBuilders.post(ROOT_PATH + "create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pushNotificationDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(pushNotificationService, times(1)).save(any());
    }

    @Test
    void test_pageable_method() throws Exception {
        MobileApplicationEntity mobileApplication = new MobileApplicationEntity();
        mobileApplication.setVersion("1.0");
        PhoneEntity phoneDto = new PhoneEntity();
        phoneDto.setToken("token");
        phoneDto.setApp(mobileApplication);
        phoneDto.setNumber("+79999999999");
        PushNotificationEntity pushNotification = new PushNotificationEntity();
        pushNotification.setBody("body");
        pushNotification.setDate(new Date());
        pushNotification.setPhones(Collections.singleton(phoneDto));
        pushNotification.setTitle("title");
        List<PushNotificationEntity> pushNotificationEntities = Collections.singletonList(pushNotification);
        String expectObject = objectMapper.writeValueAsString(Collections.singletonList(mapper.toDTO(pushNotification)));
        when(pushNotificationService.findAll(any()))
                .thenReturn(new PageImpl<>(pushNotificationEntities));
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_PATH + "pageable")
                .param("page", objectMapper.writeValueAsString(Pageable.unpaged()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(expectObject));
    }

}
