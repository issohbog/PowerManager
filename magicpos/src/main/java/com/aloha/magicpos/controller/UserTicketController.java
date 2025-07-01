package com.aloha.magicpos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aloha.magicpos.domain.UserTickets;
import com.aloha.magicpos.mapper.UserTicketMapper;

@Controller
@RequestMapping("/user-tickets")
public class UserTicketController {

    @Autowired
    private UserTicketMapper userTicketMapper;
    
     // 🔸 이용권 등록 (결제 시)
    @PostMapping
    public String insertUserTicket(@RequestBody UserTickets userTicket) {
        userTicketMapper.insert(userTicket);
        return "user_ticket_created";
    }

    // 🔸 전체 이용권 내역 조회 (관리자용)
    @GetMapping
    public List<UserTickets> getAllUserTickets() {
        return userTicketMapper.selectAll();
    }

    // 🔸 특정 유저의 이용권 내역 조회
    @GetMapping("/user/{uNo}")
    public List<UserTickets> getUserTicketsByUserNo(@PathVariable long uNo) {
        return userTicketMapper.findByUserNo(uNo);
    }

    // 🔸 특정 유저의 남은 시간 조회
    @GetMapping("/user/{uNo}/remain-time")
    public Integer getRemainTime(@PathVariable long uNo) {
        return userTicketMapper.findRemainTimeByUserNo(uNo);
    }
}
