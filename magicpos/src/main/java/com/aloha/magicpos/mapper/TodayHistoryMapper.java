package com.aloha.magicpos.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TodayHistoryMapper {
    
     // ✅ 오늘 전체 결제 내역 (주문 + 이용권)
    List<Map<String, Object>> findTodayAll();

    // ✅ 분류별 내역 조회
    List<Map<String, Object>> findTodayOrdersOnly();
    List<Map<String, Object>> findTodayTicketsOnly();

    // ✅ 전체 내역에서 검색
    List<Map<String, Object>> searchTodayAll(@Param("keyword") String keyword);

    // ✅ 주문결제 내역에서 검색
    List<Map<String, Object>> searchTodayOrders(@Param("keyword") String keyword);

    // ✅ 이용권구매 내역에서 검색
    List<Map<String, Object>> searchTodayTickets(@Param("keyword") String keyword);
}
