package com.aloha.magicpos.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.magicpos.domain.Seats;

@Mapper
public interface SeatMapper {
    // 전체 좌석 조회
    List<Seats> findAll();

    // 전체 좌석 조회 (좌석 데시보드 용 - 사용자 정보, 남은시간 도 조회)
    List<Seats> findAllSeatWithUsage();

    // 사용 중인 좌석 조회 (좌석 데시보드 용)
    int countUsingSeats();

    // 전체 좌석 개수 조회 (좌석 데시보드 용)
    int countAllSeats();

    // 단일 좌석 조회
    Seats findById(@Param("seatId") String seatId);

    // 좌석 상태 업데이트
    int updateStatus(@Param("seatId") String seatId, @Param("seatStatus") String seatStatus);

    // 좌석 사용 정보 조회 (사용자, 남은 시간 등)
    Map<String, Object> findSeatUsageInfo(@Param("seatId") String seatId);

    // 로그인한 유저 기준 좌석 사용 정보 조회
    Map<String, Object> findSeatUsageInfoByUser(@Param("userNo") Long userNo);

    // 좌석 상태 업데이트 (청소중 -> 이용가능)
    int clearSeat(@Param("seatId") String seatId);

    // 예약된 좌석ID만 반환
    List<String> findReservedSeatIds();

    // 예약되지 않은 좌석ID만 반환
    List<String> findAvailableSeatIds();

}
