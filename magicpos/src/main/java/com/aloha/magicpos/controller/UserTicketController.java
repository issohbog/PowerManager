package com.aloha.magicpos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.magicpos.domain.Tickets;
import com.aloha.magicpos.domain.UserTickets;
import com.aloha.magicpos.domain.Users;
import com.aloha.magicpos.service.TicketService;
import com.aloha.magicpos.service.UserService;
import com.aloha.magicpos.service.UserTicketService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
@RequestMapping("/usertickets")
public class UserTicketController {

    @Autowired
    private UserTicketService userticketService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;
    
    // 이용권 등록 (결제 시) - 관리자 용
    @GetMapping("/admin/tickets")
    @ResponseBody
    public List<Tickets> ticketlist(Model model) throws Exception {
        return ticketService.findAll();
    }
    
    // 이용권 등록 전 회원 검색 용
    @GetMapping("/admin/usersearch")
    @ResponseBody
    public List<Map<String,Object>> searchUserByKeywordList(@RequestParam("keyword") String keyword) throws Exception {
        List<Users> users = userService.searchUsersByKeyword(keyword);

        return users.stream().map(user -> {
            Map<String,Object> map = new HashMap<>();
            map.put("userNo", user.getNo());
            map.put("username", user.getUsername());
            map.put("userId", user.getId());
            return map;
        }).collect(Collectors.toList());
    }
    



    
     // 🔸 이용권 등록 (결제 시) - 사용자 화면용
    @PostMapping("/insert")
    @ResponseBody
    public String insertUserTicket(@RequestBody UserTickets userTicket) throws Exception {
        log.info("🧾 받은 userTicket = {}", userTicket);

        // 임시로 setter 강제 사용
        if (userTicket.getUNo() == null) {
            log.error("🔥 uNo가 null이야!");
        }

        
        boolean success = userticketService.insert(userTicket);
        return success ? "success" : "fail";
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
