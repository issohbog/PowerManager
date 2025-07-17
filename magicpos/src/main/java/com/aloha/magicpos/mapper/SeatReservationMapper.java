package com.aloha.magicpos.mapper;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeatReservationMapper {
    Long getTotalUsedTime(Long userNo);
    
    // 좌석 예약 정보 저장
    // void insertSeatReservation(
    //     @Param("seatId") String seatId,
    //     @Param("userNo") Long userNo,
    //     @Param("startTime") java.sql.Timestamp startTime,
    //     @Param("endTime") java.sql.Timestamp endTime
    // );

    int countUsingSeatByUser(Long userNo);
} 