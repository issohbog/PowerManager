package com.aloha.magicpos.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.domain.Seats;
import com.aloha.magicpos.mapper.SeatMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("SeatService")
public class SeatServiceImpl implements SeatService {
    
    @Autowired private SeatMapper seatMapper;

    @Override
    public List<Seats> findAll() throws Exception {
        return seatMapper.findAll();
    }


    @Override
    public Seats findById(String seatId) throws Exception {
        return seatMapper.findById(seatId);
    }

    @Override
    public boolean updateStatus(String seatId, String seatStatus) throws Exception {
        return seatMapper.updateStatus(seatId, seatStatus) > 0; 
    }

    @Override
    public Map<String, Object> findSeatUsageInfo(String seatId) throws Exception {
        return seatMapper.findSeatUsageInfo(seatId);
    }


    @Override
    public Map<String, Object> findSeatUsageInfoByUser(Long userNo) throws Exception {
        return seatMapper.findSeatUsageInfoByUser(userNo);
    }

}
