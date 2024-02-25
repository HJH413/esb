package com.engis.esb.api.timeCheck.controller;

import com.engis.esb.api.timeCheck.request.LoginRequest;
import com.engis.esb.api.timeCheck.request.TimeRequest;
import com.engis.esb.api.timeCheck.response.LoginResponse;
import com.engis.esb.api.timeCheck.response.TimeResponse;
import com.engis.esb.api.timeCheck.service.TimeCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TimeCheckController {
    private final TimeCheckService timeCheckService;

    // 로그인
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return timeCheckService.login(loginRequest);
    }

    // 시간조회
    @GetMapping("/time/{userNo}")
    public TimeResponse getTime(@PathVariable Long userNo) {
        return timeCheckService.getTime(userNo);
    }

    // 출근하기
    @PostMapping("/time")
    public TimeResponse insertTime(@RequestBody TimeRequest timeRequest) {
        return timeCheckService.insertTime(timeRequest);
    }

    // 퇴근하기
    @PatchMapping("/time/{timeCheckNo}")
    public TimeResponse updateTime(@PathVariable Long timeCheckNo) {
        return timeCheckService.updateTime(timeCheckNo);
    }
}
