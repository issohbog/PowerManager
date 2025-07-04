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

    // 단일 좌석 조회
    Seats findById(@Param("seatId") String seatId);

    // 좌석 상태 업데이트
    int updateStatus(@Param("seatId") String seatId, @Param("seatStatus") String seatStatus);

    // 좌석 사용 정보 조회 (사용자, 남은 시간 등)
    Map<String, Object> findSeatUsageInfo(@Param("seatId") String seatId);
}
