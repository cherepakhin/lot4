package ru.stm.lot4.statistic.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.stm.lot4.repository.MobileApplicationRepository;
import ru.stm.lot4.statistic.service.StatisticService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticController.class)
public class StatisticServiceControllerTest {
    private final static String STATISTIC_APP_ENDPOINT = "/statistic/application";
    private final static String STATISTIC_MESSAGES_ENDPOINT = "/statistic/messages";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticService statisticService;
    @MockBean
    private MobileApplicationRepository mobileApplicationRepository;

    @Test
    void getApplicationStatisticTest() throws Exception {
        this.mockMvc.perform(get(STATISTIC_APP_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(statisticService, times(1)).getApplicationStat();
    }

    @Test
    void getApplicationStatisticNotAllowedTest() throws Exception {
        this.mockMvc.perform(post(STATISTIC_APP_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
        verify(statisticService, times(0)).getApplicationStat();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "+79999999999",
            "79999999999",
            "89999999999",
            "9999999999"
    })
    void getMessagesByPhonePositiveTest(String phone) throws Exception {
        this.mockMvc.perform(get(STATISTIC_MESSAGES_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .param("phone", phone))
                .andDo(print())
                .andExpect(status().isOk());
        verify(statisticService, times(1)).getMessagesByPhone(anyString(), any());
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "adfererere",
            "-79999999999",
            "+799f9ff9999",
            "",
            "null"
    })
    void getMessagesByPhoneNegativeTest(String phone) throws Exception {
        this.mockMvc.perform(get(STATISTIC_MESSAGES_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .param("phone", phone))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verify(statisticService, times(0)).getMessagesByPhone(anyString(), any());
    }
}
