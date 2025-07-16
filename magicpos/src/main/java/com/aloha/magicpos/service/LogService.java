package com.aloha.magicpos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface LogService {

    // ✅ 로그 삽입
    public void insertLog(Long uNo, String seatId, String actionType, String description);

    // 🔍 검색 포함
    public List<Map<String, Object>> searchLoginLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception;
    public List<Map<String, Object>> searchJoinLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception;
    public List<Map<String, Object>> searchTicketLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception;
    public List<Map<String, Object>> searchProductLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception;
    public List<Map<String, Object>> searchAllLogsByDate(LocalDate startDate, LocalDate endDate, String keyword) throws Exception;

    // 📄 검색 없이
    public List<Map<String, Object>> findLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception;
    public List<Map<String, Object>> findLoginLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception;
    public List<Map<String, Object>> findJoinLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception;
    public List<Map<String, Object>> findTicketLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception;
    public List<Map<String, Object>> findProductLogsByDate(LocalDate startDate, LocalDate endDate) throws Exception;


}
