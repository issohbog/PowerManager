package com.aloha.magicpos.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.domain.SeatsReservations;
import com.aloha.magicpos.mapper.SeatReservationMapper;

@Service
public class SeatReservationServiceImpl implements SeatReservationService {

    @Autowired
    SeatReservationMapper seatReservationMapper;

    @Autowired
    SeatService seatService;

    @Autowired  
    UserTicketService userTicketService;

    @Override
    public Long getTotalUsedTime(Long userNo) {
        return seatReservationMapper.getTotalUsedTime(userNo);
    }

    @Override
    public SeatsReservations autoReserveRandomSeatForUser(Long userNo, String seatId) throws Exception {
        // 0. 사용자가 이미 이용중인 좌석이 있는지 확인 
        // int usingCount = this.countUsingSeatByUser(userNo);
        // if (usingCount > 0) {
        //     throw new IllegalStateException("이미 이용중인 좌석이 있습니다.");
        // }

        
        // 1. 예약되지 않은 seat_id 조회 
        // List<String> availableSeats = seatService.findAvailableSeatIds();
        // if(availableSeats == null || availableSeats.isEmpty()){
        //     throw new IllegalStateException("예약 가능한 좌석이 없습니다.");
        // }

        

        // 3. user_ticket에서 remain_time 합산 
        Long remainTimeLong = userTicketService.getTotalRemainTime(userNo);
        Integer remainTime = remainTimeLong != null ? remainTimeLong.intValue() : 0;

        // 4. start_time = 현재 시각 
        LocalDateTime startTime = LocalDateTime.now();

        // 5. end_time = start_time + remain_time 
        LocalDateTime endTime = startTime.plusMinutes(remainTime);

        // 6. seat_reservation 테이블에 예약 정보 저장 
        // this.insertSeatReservation(
        //     seatId,
        //     userNo,
        //     Timestamp.valueOf(startTime),
        //     Timestamp.valueOf(endTime)
        // );

        // 7. 예약 정보 반환 
        SeatsReservations seatReservation = new SeatsReservations();
        seatReservation.setSeatId(seatId);
        seatReservation.setUNo(userNo);
        seatReservation.setStartTime(Timestamp.valueOf(startTime));
        seatReservation.setEndTime(Timestamp.valueOf(endTime));
        
        return seatReservation;

    }

    // @Override
    // public void insertSeatReservation(
    //         @Param("seatId") String seatId, 
    //         @Param("userNo") Long userNo, 
    //         @Param("startTime") Timestamp startTime, 
    //         @Param("endTime") Timestamp endTime) {
    //     seatReservationMapper.insertSeatReservation(seatId, userNo, startTime, endTime);
    // }

    @Override
    public int countUsingSeatByUser(Long userNo) throws Exception   {
        return seatReservationMapper.countUsingSeatByUser(userNo);
    }
}   