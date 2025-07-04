package com.aloha.magicpos.service;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<Seats> findAllSeatWithUsage() {

        
        List<Seats> list = seatMapper.findAllSeatWithUsage();

        // 화면용 className 추가 계산 
        for (Seats seat : list) {

            // System.out.println("seatId = " + seat.getSeatId() + ", remainTime = " + seat.getRemainTime());


            String className = switch (seat.getSeatStatus().intValue()){
                case 2 -> "broken";
                case 3 -> "cleaning"; 
                case 1 -> (seat.getRemainTime() != null && seat.getRemainTime() > 60)
                            ? "in-use-green"
                            : "in-use-red"; 
                default -> "available";
            };
            seat.setClassName(className);
        }
        return list;
    }

    @Override
    public Map<String, List<Seats>> getSeatSections() {
        List<Seats> allSeats = findAllSeatWithUsage();

        List<Seats> top = new ArrayList<>();
        List<Seats> middle = new ArrayList<>();
        List<Seats> bottom = new ArrayList<>();

        for (Seats seat : allSeats) {
            int num = Integer.parseInt(seat.getSeatId().substring(1));
            if (num <= 12) top.add(seat);
            else if (num <= 22) middle.add(seat);
            else bottom.add(seat);
        }
         
        Map<String, List<Seats>> map = new HashMap<>();
        map.put("topSeats", top);
        map.put("middleSeats", middle);
        map.put("bottomSeats", bottom);

        return map;
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








}
