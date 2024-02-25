package com.engis.esb.api.timeCheck.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EsbTimeCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeCheckNo;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private EsbUser esbUser;

    // 출근 시간 저장 생성사
    public EsbTimeCheck(EsbUser esbUser) {
        this.esbUser = esbUser;
        this.startTime = LocalDateTime.now();
    }

    // 퇴근 시간을 현재 시간으로 업데이트하는 메소드
    public void updateEndTime() {
        this.endTime = LocalDateTime.now();
    }
}
