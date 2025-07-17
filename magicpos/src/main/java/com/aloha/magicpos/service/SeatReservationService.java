package com.aloha.magicpos.service;

import java.sql.Timestamp;
import com.aloha.magicpos.domain.SeatsReservations;

public interface SeatReservationService {
    Long getTotalUsedTime(Long userNo);

    // 좌석 랜덤 선택 후 시간 계산 후 예약 정보 저장
    public SeatsReservations autoReserveRandomSeatForUser(Long userNo, String seatId) throws Exception;

    // 좌석 예약 정보 저장
    // public void insertSeatReservation(String seatId, Long userNo, Timestamp startTime, Timestamp endTime);

    // 사용자가 이미 이용중인 좌석이 있는지 확인
    public int countUsingSeatByUser(Long userNo) throws Exception;
}
