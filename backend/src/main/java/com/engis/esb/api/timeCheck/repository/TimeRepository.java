package com.engis.esb.api.timeCheck.repository;

import com.engis.esb.api.timeCheck.domain.EsbTimeCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<EsbTimeCheck, Long> {

    /*
    1. 유저의 정보를 기준으로 오늘날짜를 기준으로 출퇴근 데이터 조회
    2. 오늘날짜의 데이터가 없다면 어제 일자의 출퇴근 데이터 조회 단, 퇴근일자는 null 이어야함
        ex : 23일 출근 24일 02시 퇴근 찍기
     */
    @Query(value = "SELECT * " +
                    "FROM esb_time_check " +
                    "WHERE user_no = :userNo " +
                    "AND (CAST(start_time AS DATE) = CURRENT_DATE OR " +
                        "(CAST(start_time AS DATE) = CURRENT_DATE - INTERVAL '1 day' AND end_time IS NULL)) " +
                "ORDER BY CAST(start_time AS DATE) DESC, " +
                          "end_time DESC NULLS FIRST " +
                   "LIMIT 1",
            nativeQuery = true)
    Optional<EsbTimeCheck> findTime(@Param("userNo") Long userNo);

    /*
        퇴근 시간 업데이트
     */
    @Modifying
    @Transactional
    @Query("UPDATE EsbTimeCheck t SET t.endTime = :yesterdaySixPM WHERE t.endTime IS NULL")
    void updateEndTimeForNullRecords(LocalDateTime yesterdaySixPM);
}
