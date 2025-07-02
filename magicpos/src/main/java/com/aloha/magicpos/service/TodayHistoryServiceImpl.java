package com.aloha.magicpos.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.mapper.TodayHistoryMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("TodayHistoryService")
public class TodayHistoryServiceImpl implements TodayHistoryService {

    @Autowired private TodayHistoryMapper todayHistoryMapper;

    @Override
    public List<Map<String, Object>> findTodayAll() throws Exception {
        return todayHistoryMapper.findTodayAll();
    }


    @Override
    public List<Map<String, Object>> findTodayOrdersOnly() throws Exception {
        return todayHistoryMapper.findTodayOrdersOnly();
    }


    @Override
    public List<Map<String, Object>> findTodayTicketsOnly() throws Exception {
        return todayHistoryMapper.findTodayTicketsOnly();
    }

    @Override
    public List<Map<String, Object>> searchTodayAll(String keyword) throws Exception {
        return todayHistoryMapper.searchTodayAll(keyword);
    }

    @Override
    public List<Map<String, Object>> searchTodayOrders(String keyword) throws Exception {
        return todayHistoryMapper.searchTodayOrders(keyword);
    }

    @Override
    public List<Map<String, Object>> searchTodayTickets(String keyword) throws Exception {
        return todayHistoryMapper.searchTodayTickets(keyword);
    }
    
}
