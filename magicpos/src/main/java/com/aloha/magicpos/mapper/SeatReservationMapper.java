package com.aloha.magicpos.mapper;

<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 426af1e44cc020d270df0c41f7b497bea116d066
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
=======
>>>>>>> main
=======

>>>>>>> 426af1e44cc020d270df0c41f7b497bea116d066
import org.apache.ibatis.annotations.Mapper;

import com.aloha.magicpos.domain.SeatsReservations;

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

    // 유저의 가장 최근 예약 정보 가져오기
    SeatsReservations findCurrentReservationByUser(Long userNo);

    // 현재 이용중인 좌석 조회(관리자용)
    List<Map<String, Object>> findCurrentSeatUsage();
} 