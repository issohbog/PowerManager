package com.aloha.magicpos.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LogMapper {

    void insertLog(
    @Param("uNo") Long uNo,
    @Param("seatId") String seatId,
    @Param("actionType") String actionType,
    @Param("description") String description
    );

    void insertLogNoSeatId(
        @Param("uNo") Long uNo,
        @Param("actionType") String actionType,
        @Param("description") String description
    );

    // 🔍 검색 포함
    List<Map<String, Object>> searchLoginLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("keyword") String keyword);

    List<Map<String, Object>> searchJoinLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("keyword") String keyword);

    List<Map<String, Object>> searchTicketLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,  @Param("keyword") String keyword);

    List<Map<String, Object>> searchProductLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,  @Param("keyword") String keyword);

    List<Map<String, Object>> searchAllLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,  @Param("keyword") String keyword);

    // 📄 검색 없이
    List<Map<String, Object>> findLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Map<String, Object>> findLoginLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Map<String, Object>> findJoinLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Map<String, Object>> findTicketLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Map<String, Object>> findProductLogsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
