package com.engis.esb.api.timeCheck.service;

import com.engis.esb.api.timeCheck.domain.EsbTimeCheck;
import com.engis.esb.api.timeCheck.domain.EsbUser;
import com.engis.esb.api.timeCheck.repository.TimeRepository;
import com.engis.esb.api.timeCheck.repository.UserRepository;
import com.engis.esb.api.timeCheck.request.LoginRequest;
import com.engis.esb.api.timeCheck.request.TimeRequest;
import com.engis.esb.api.timeCheck.response.LoginResponse;
import com.engis.esb.api.timeCheck.response.TimeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeCheckService {
    private final UserRepository userRepository;
    private final TimeRepository timeRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        return userRepository.findByUserIdAndUserPw(loginRequest.getUserId(), loginRequest.getUserPw())
                .map(user -> new LoginResponse(user.getUserNo(), user.getUserName(), true))
                .orElse(new LoginResponse(null, null, false));
    }

    public TimeResponse getTime(Long userNo) {
        return timeRepository.findTime(userNo).map(time ->
                TimeResponse.builder()
                        .timeCheckNo(time.getTimeCheckNo())
                        .startDate(time.getStartTime().toLocalDate())
                        .startTime(time.getStartTime().toLocalTime().truncatedTo(ChronoUnit.SECONDS))
                        .endDate(time.getEndTime() != null ? time.getEndTime().toLocalDate() : null)
                        .endTime(time.getEndTime() != null ? time.getEndTime().toLocalTime().truncatedTo(ChronoUnit.SECONDS) : null)
                        .build()
        ).orElse(null); // 결과가 없는 경우 null 반환
    }

    public TimeResponse insertTime(TimeRequest timeRequest) {
        EsbUser esbUser = userRepository.findById(timeRequest.getUserNo()).orElseThrow(() -> new RuntimeException("유저 정보 없음"));

        EsbTimeCheck insertTime = new EsbTimeCheck(esbUser);
        EsbTimeCheck saveTime = timeRepository.save(insertTime);

        return TimeResponse.builder()
                .timeCheckNo(saveTime.getTimeCheckNo())
                .startDate(saveTime.getStartTime().toLocalDate())
                .startTime(saveTime.getStartTime().toLocalTime().truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    public TimeResponse updateTime(Long timeCheckNo) {
        EsbTimeCheck updateTime = timeRepository.findById(timeCheckNo).orElseThrow(() -> new RuntimeException("업데이트 정보 없음"));

        updateTime.updateEndTime(); // 퇴근 시간 업데이트
        timeRepository.save(updateTime); // 변경 사항 저장

        return TimeResponse.builder()
                .timeCheckNo(updateTime.getTimeCheckNo())
                .startDate(updateTime.getStartTime().toLocalDate())
                .startTime(updateTime.getStartTime().toLocalTime().truncatedTo(ChronoUnit.SECONDS))
                .endDate(updateTime.getEndTime() != null ? updateTime.getEndTime().toLocalDate() : null)
                .endTime(updateTime.getEndTime() != null ? updateTime.getEndTime().toLocalTime().truncatedTo(ChronoUnit.SECONDS) : null)
                .build();
    }

    // 매일 새벽 8시(KST)에 실행되는 스케줄링된 작업
    @Scheduled(cron = "0 0 23 * * ?", zone = "UTC") // UTC 기준 오후 11시 (KST 새벽 8시)
    public void updateNullEndTime() {
        // 전날의 오후 6시를 계산합니다. (KST 기준으로 작업이 수행되므로, 시간 계산 시 시간대를 고려해야 합니다)
        LocalDateTime yesterdaySixPM = LocalDateTime.now().minusDays(1)
                .withHour(18) // 오후 6시
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        // 퇴근 시간이 null인 레코드를 일괄 업데이트하는 로직 구현
        timeRepository.updateEndTimeForNullRecords(yesterdaySixPM);
    }
}
