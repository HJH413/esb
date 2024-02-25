package com.engis.esb.api.timeCheck.service;

import com.engis.esb.api.timeCheck.request.LoginRequest;
import com.engis.esb.api.timeCheck.response.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TimeCheckServiceTest {
    @Autowired
    private TimeCheckService timeCheckService;

    @Test
    @DisplayName("유저번호, 유저이름 호출 성공")
    void test1() {
        LoginRequest loginRequest = LoginRequest.builder()
                .userId("hjh")
                .userPw("1234")
                .build();

        LoginResponse loginResponse = timeCheckService.login(loginRequest);
        assertEquals("홍진혁", loginResponse.getUserName(), "유저 이름이 일치하지 않습니다.");
    }

    @Test
    @DisplayName("유저번호, 유저이름 호출 실패")
    void test2() {
        LoginRequest loginRequest = LoginRequest.builder()
                .userId("hjh")
                .userPw("12345")
                .build();

        LoginResponse loginResponse = timeCheckService.login(loginRequest);
        assertFalse(loginResponse.isSuccess());
    }

}