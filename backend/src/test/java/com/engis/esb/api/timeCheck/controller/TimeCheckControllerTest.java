package com.engis.esb.api.timeCheck.controller;

import com.engis.esb.api.timeCheck.request.LoginRequest;
import com.engis.esb.api.timeCheck.response.LoginResponse;
import com.engis.esb.api.timeCheck.service.TimeCheckService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TimeCheckControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TimeCheckService timeCheckService;

    @Test
    @DisplayName("로그인 성공 테스트")
    void test1() throws Exception {
        // 준비
        LoginRequest loginRequest = new LoginRequest("hjh", "1234");
        LoginResponse loginResponse = new LoginResponse(1L, "홍진혁", true);


        // 실행 & 검증
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userNo").value("1L"))
                .andExpect(jsonPath("$.userName").value("홍진혁"));
    }
}