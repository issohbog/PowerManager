package com.aloha.magicpos.service;

import java.util.List;
import java.util.Map;


public interface TodayHistoryService {
    // ✅ 오늘 전체 결제 내역 (주문 + 이용권)
    public List<Map<String, Object>> findTodayAll() throws Exception;

    // ✅ 분류별 내역 조회
    public List<Map<String, Object>> findTodayOrdersOnly() throws Exception;
    public List<Map<String, Object>> findTodayTicketsOnly() throws Exception;

    // ✅ 전체 내역에서 검색
    public List<Map<String, Object>> searchTodayAll(String keyword) throws Exception;

    // ✅ 주문결제 내역에서 검색
    public List<Map<String, Object>> searchTodayOrders(String keyword) throws Exception;

    // ✅ 이용권구매 내역에서 검색
    public List<Map<String, Object>> searchTodayTickets(String keyword) throws Exception;
}
