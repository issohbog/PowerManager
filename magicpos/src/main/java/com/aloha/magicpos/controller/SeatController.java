package com.aloha.magicpos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.magicpos.domain.Seats;
import com.aloha.magicpos.mapper.SeatMapper;

@Controller
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatMapper seatMapper;

    // ✅ 전체 좌석 목록 조회
    @GetMapping
    public String list(Model model) {
        List<Seats> seats = seatMapper.findAll();
        model.addAttribute("seats", seats);
        return "seat/list";
    }

    // ✅ 단일 좌석 상세 조회
    @GetMapping("/{seatId}")
    public String detail(@PathVariable("seatId") String seatId, Model model) {
        Seats seat = seatMapper.findById(seatId);
        model.addAttribute("seat", seat);
        return "seat/detail";
    }

    // ✅ 좌석 상태 변경
    @PostMapping("/{seatId}/status")
    public String updateStatus(@PathVariable("seatId") String seatId,
                               @RequestParam("seatStatus") String seatStatus) {
        seatMapper.updateStatus(seatId, seatStatus);
        return "redirect:/seats";
    }

    // ✅ 좌석 사용 정보 조회 (남은 시간, 사용자 포함)
    @GetMapping("/{seatId}/usage")
    @ResponseBody
    public Map<String, Object> usageInfo(@PathVariable("seatId") String seatId) {
        return seatMapper.findSeatUsageInfo(seatId);
    }
}
