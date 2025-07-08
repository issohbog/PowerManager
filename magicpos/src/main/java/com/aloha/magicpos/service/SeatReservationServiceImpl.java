package com.aloha.magicpos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.mapper.SeatReservationMapper;

@Service
public class SeatReservationServiceImpl implements SeatReservationService {

    @Autowired
    SeatReservationMapper seatReservationMapper;

    @Override
    public Long getTotalUsedTime(Long userNo) {
        return seatReservationMapper.getTotalUsedTime(userNo);
    }


}
