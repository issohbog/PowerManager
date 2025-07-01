package com.aloha.magicpos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.mapper.TodayHistoryMapper;

@Controller
@RequestMapping("/history/today")
public class TodayHistoryController {

    @Autowired
    private TodayHistoryMapper todayHistoryMapper;
    
     // 🔸 오늘 전체 결제 내역 (주문 + 이용권)
    @GetMapping("/all")
    public List<Map<String, Object>> getTodayAll() {
        return todayHistoryMapper.findTodayAll();
    }

    // 🔸 오늘 주문 결제 내역만
    @GetMapping("/orders")
    public List<Map<String, Object>> getTodayOrdersOnly() {
        return todayHistoryMapper.findTodayOrdersOnly();
    }

    // 🔸 오늘 이용권 결제 내역만
    @GetMapping("/tickets")
    public List<Map<String, Object>> getTodayTicketsOnly() {
        return todayHistoryMapper.findTodayTicketsOnly();
    }

    // 🔍 전체 내역에서 키워드 검색
    @GetMapping("/search/all")
    public List<Map<String, Object>> searchTodayAll(@RequestParam String keyword) {
        return todayHistoryMapper.searchTodayAll(keyword);
    }

    // 🔍 주문 내역에서 키워드 검색
    @GetMapping("/search/orders")
    public List<Map<String, Object>> searchTodayOrders(@RequestParam String keyword) {
        return todayHistoryMapper.searchTodayOrders(keyword);
    }

    // 🔍 이용권 내역에서 키워드 검색
    @GetMapping("/search/tickets")
    public List<Map<String, Object>> searchTodayTickets(@RequestParam String keyword) {
        return todayHistoryMapper.searchTodayTickets(keyword);
    }
}
