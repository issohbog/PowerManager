package com.aloha.magicpos.mapper;

import org.apache.ibatis.annotations.Mapper;

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