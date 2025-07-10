package com.aloha.magicpos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.service.TodayHistoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/history/today")
public class TodayHistoryController {

    
    @Autowired
    private TodayHistoryService todayHistoryService;
    
    @GetMapping("/all")
    public String getTodayHistoryPage(@RequestParam(name = "keyword", required = false) String keyword,
                                    @RequestParam(name = "type", required = false) String type,
                                    Model model) throws Exception {
        List<Map<String, Object>> todayList;
        
        if (type == null) type = "";

        if (keyword != null && !keyword.isEmpty()) {
            // 📌 검색이 들어온 경우 + 타입 분기
            switch (type) {
                case "orderhistory":
                    todayList = todayHistoryService.searchTodayOrders(keyword);
                    keyword = ""; // 검색어 초기화
                    break;
                case "tickethistory":
                    todayList = todayHistoryService.searchTodayTickets(keyword);
                    keyword = ""; // 검색어 초기화
                    break;
                default:
                    todayList = todayHistoryService.searchTodayAll(keyword);
                    keyword = ""; // 검색어 초기화
            }
        } else {
            // 📌 검색이 없고 타입만 분기
            switch (type) {
                case "orderhistory":
                    todayList = todayHistoryService.findTodayOrdersOnly();
                    break;
                case "tickethistory":
                    todayList = todayHistoryService.findTodayTicketsOnly();
                    break;
                default:
                    todayList = todayHistoryService.findTodayAll();
            }
        }
        // ✨ 화면 유지를 위해 검색어와 타입은 그대로 전달
        model.addAttribute("todayList", todayList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        log.info("조회된 오늘의 내역 수: {}", todayList.size());
        
        return "pages/admin/admin_today_list";
    }

    // 🔸 오늘 전체 결제 내역 (주문 + 이용권)
    @GetMapping("/allnotuse")
    public List<Map<String, Object>> getTodayAll() throws Exception {
        return todayHistoryService.findTodayAll();
    }

    // 🔸 오늘 주문 결제 내역만
    @GetMapping("/orders")
    public List<Map<String, Object>> getTodayOrdersOnly() throws Exception {
        return todayHistoryService.findTodayOrdersOnly();
    }

    // 🔸 오늘 이용권 결제 내역만
    @GetMapping("/tickets")
    public List<Map<String, Object>> getTodayTicketsOnly() throws Exception {
        return todayHistoryService.findTodayTicketsOnly();
    }

    // 🔍 전체 내역에서 키워드 검색
    @GetMapping("/search/all")
    public List<Map<String, Object>> searchTodayAll(@RequestParam String keyword) throws Exception {
        return todayHistoryService.searchTodayAll(keyword);
    }

    // 🔍 주문 내역에서 키워드 검색
    @GetMapping("/search/orders")
    public List<Map<String, Object>> searchTodayOrders(@RequestParam String keyword) throws Exception {
        return todayHistoryService.searchTodayOrders(keyword);
    }

    // 🔍 이용권 내역에서 키워드 검색
    @GetMapping("/search/tickets")
    public List<Map<String, Object>> searchTodayTickets(@RequestParam String keyword) throws Exception {
        return todayHistoryService.searchTodayTickets(keyword);
    }
}
