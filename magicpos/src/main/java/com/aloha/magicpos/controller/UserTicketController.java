package com.aloha.magicpos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aloha.magicpos.domain.UserTickets;
import com.aloha.magicpos.domain.Users;
import com.aloha.magicpos.service.SeatService;
import com.aloha.magicpos.service.UserTicketService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/user-tickets")
public class UserTicketController {

    @Autowired
    private UserTicketService userticketService;

    @Autowired
    private SeatService seatService;

    @GetMapping("/buy")
    public String userTicketBuy(HttpSession session,  Model model) throws Exception {

            // 도와주세요... 

    return "pages/users/userticket_buy";

    }
    
    
     // 🔸 이용권 등록 (결제 시)
    @PostMapping
    public String insertUserTicket(@RequestBody UserTickets userTicket) throws Exception {
        userticketService.insert(userTicket);
        return "user_ticket_created";
    }

    // 🔸 전체 이용권 내역 조회 (관리자용)
    @GetMapping
    public List<UserTickets> getAllUserTickets() throws Exception {
        return userticketService.selectAll();
    }

    // 🔸 특정 유저의 이용권 내역 조회
    @GetMapping("/user/{uNo}")
    public List<UserTickets> getUserTicketsByUserNo(@PathVariable long uNo) throws Exception {
        return userticketService.findByUserNo(uNo);
    }

    // 🔸 특정 유저의 남은 시간 조회
    @GetMapping("/user/{uNo}/remain-time")
    public Integer getRemainTime(@PathVariable long uNo) throws Exception {
        return userticketService.findRemainTimeByUserNo(uNo);
    }
}
