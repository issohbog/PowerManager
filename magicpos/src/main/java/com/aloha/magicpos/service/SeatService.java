package com.aloha.magicpos.service;

import java.util.List;
import java.util.Map;

import com.aloha.magicpos.domain.Seats;

public interface SeatService {
    // 전체 좌석 조회
    public List<Seats> findAll() throws Exception;

    // 전체 좌석 조회 (좌석 데시보드 용 - 사용자 정보, 남은시간 도 조회)
    List<Seats> findAllSeatWithUsage();

    // 좌석 구간별 분리 
    Map<String, List<Seats>> getSeatSections();

    // 단일 좌석 조회
    public Seats findById(String seatId) throws Exception;

    // 좌석 상태 업데이트
    public boolean updateStatus(String seatId, String seatStatus) throws Exception;

    // 좌석 사용 정보 조회 (사용자, 남은 시간 등) throws Exception
    public Map<String, Object> findSeatUsageInfo(String seatId) throws Exception;
}
