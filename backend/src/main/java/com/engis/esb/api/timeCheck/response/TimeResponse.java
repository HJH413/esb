package com.engis.esb.api.timeCheck.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class TimeResponse {

    private Long timeCheckNo;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    @Builder
    public TimeResponse(Long timeCheckNo, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        this.timeCheckNo = timeCheckNo;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }
}
