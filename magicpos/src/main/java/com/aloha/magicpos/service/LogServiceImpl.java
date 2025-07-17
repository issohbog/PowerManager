package com.aloha.magicpos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.mapper.LogMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("LogService")
public class LogServiceImpl implements LogService{
    @Autowired
    private LogMapper logMapper;

    // 로그 삽입
    @Override
    public void insertLog(Long uNo, String seatId, String actionType, String description) {
        logMapper.insertLog(uNo, seatId, actionType, description);
    }

    // 로그 삽입 seat_id : X
    @Override
    public void insertLogNoSeatId(Long uNo, String actionType, String description) {
        logMapper.insertLogNoSeatId(uNo, actionType, description);
    }
    

    // 🔍 검색 포함
    @Override
    public List<Map<String, Object>> searchLoginLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception {
        return logMapper.searchLoginLogsByDate(startDate,endDate, keyword);
    }

    @Override
    public List<Map<String, Object>> searchJoinLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception {
        return logMapper.searchJoinLogsByDate(startDate,endDate, keyword);
    }

    @Override
    public List<Map<String, Object>> searchTicketLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception {
        return logMapper.searchTicketLogsByDate(startDate,endDate, keyword);
    }

    @Override
    public List<Map<String, Object>> searchProductLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception {
        return logMapper.searchProductLogsByDate(startDate,endDate, keyword);
    }

    @Override
    public List<Map<String, Object>> searchAllLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception {
        return logMapper.searchAllLogsByDate(startDate,endDate, keyword);
    }

    // 📄 검색 없이
    @Override
    public List<Map<String, Object>> findLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception {
        return logMapper.findLogsByDate(startDate,endDate);
    }
    
    @Override
    public List<Map<String, Object>> findLoginLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception {
        return logMapper.findLoginLogsByDate(startDate,endDate);
    }

    @Override
    public List<Map<String, Object>> findJoinLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception {
        return logMapper.findJoinLogsByDate(startDate,endDate);
    }

    @Override
    public List<Map<String, Object>> findTicketLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception {
        return logMapper.findTicketLogsByDate(startDate,endDate);
    }

    @Override
    public List<Map<String, Object>> findProductLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception {
        return logMapper.findProductLogsByDate(startDate,endDate);
    }


}
