package com.aloha.magicpos.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.service.LogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/logList")
    public String getLogList(
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "type", required = false) String type,
        @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
        Model model
    ) throws Exception {
        List<Map<String, Object>> logList;

        if (startDate == null) {
            startDate = LocalDate.now(); // 기본값: 오늘
        }
        if (endDate == null) {
            endDate = LocalDate.now(); // 기본값: 오늘
        }
        if (type == null) type = "";

        if (keyword != null && !keyword.isEmpty()) {
            switch (type) {
                case "loginhistory":
                    logList = logService.searchLoginLogsByDate(startDate, endDate, keyword);
                    break;
                case "joinhistory":
                    logList = logService.searchJoinLogsByDate(startDate, endDate, keyword);
                    break;
                case "tickethistory":
                    logList = logService.searchTicketLogsByDate(startDate, endDate, keyword);
                    break;
                case "orderhistory":
                    logList = logService.searchProductLogsByDate(startDate, endDate, keyword);
                    break;
                default:
                    logList = logService.searchAllLogsByDate(startDate, endDate, keyword);
            }
        } else {
            switch (type) {
                case "loginhistory":
                    logList = logService.findLoginLogsByDate(startDate, endDate);
                    break;
                case "joinhistory":
                    logList = logService.findJoinLogsByDate(startDate, endDate);
                    break;
                case "tickethistory":
                    logList = logService.findTicketLogsByDate(startDate, endDate);
                    break;
                case "orderhistory":
                    logList = logService.findProductLogsByDate(startDate, endDate);
                    break;
                default:
                    logList = logService.findLogsByDate(startDate, endDate);
            }
        }

        model.addAttribute("logList", logList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        log.info("조회된 로그 수: {}", logList.size());

        return "pages/admin/admin_log_list";
    }
}
