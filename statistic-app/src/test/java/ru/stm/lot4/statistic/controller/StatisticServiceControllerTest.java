package ru.stm.lot4.statistic.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.stm.lot4.statistic.service.StatisticService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//todo fix run
@WebMvcTest(StatisticController.class)
public class StatisticServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticService statisticService;

   // @Test
    void getApplicationStatisticTest() throws Exception {
        this.mockMvc.perform(get("/statistic/application")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(statisticService, times(1)).getApplicationStat();
    }

   // @Test
    void getApplicationStatisticNotAllowedTest() throws Exception {
        this.mockMvc.perform(post("/statistic/application")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
        verify(statisticService, times(0)).getApplicationStat();
    }

   // @Test
    void getMessagesByPhonePlusSevenTest() throws Exception {
        this.mockMvc.perform(get("/statistic/messages")
                .contentType("application/json")
                .param("phone", "+79999999999"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(statisticService, times(1)).getMessagesByPhone(anyString(), any());
    }

  //  @Test
    void getMessagesByPhoneSevenTest() throws Exception {
        this.mockMvc.perform(get("/statistic/messages")
                .contentType("application/json")
                .param("phone", "79999999999"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(statisticService, times(1)).getMessagesByPhone(anyString(), any());
    }

  //  @Test
    void getMessagesByPhoneEightTest() throws Exception {
        this.mockMvc.perform(get("/statistic/messages")
                .contentType("application/json")
                .param("phone", "89999999999"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(statisticService, times(1)).getMessagesByPhone(anyString(), any());
    }

  //  @Test
    void getMessagesByTextPhoneTest() throws Exception {
        this.mockMvc.perform(get("/statistic/messages")
                .contentType("application/json")
                .param("phone", "+799f9ff9999"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verify(statisticService, times(0)).getMessagesByPhone(anyString(), any());
    }
}
