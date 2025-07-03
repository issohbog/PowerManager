package com.aloha.magicpos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aloha.magicpos.domain.Seats;
import com.aloha.magicpos.domain.Users;

@Controller
public class HomeController {
    @GetMapping("/menu")
    public String showMenuPage(Model model) {
        // 사용자 정보 세팅 (임시)
        Users user = new Users();
        user.setUsername("user123"); // 임시 유저 이름

        model.addAttribute("user", user);
        
        Seats seat = new Seats();
        seat.setSeatId("A10");
        seat.setSeatName("10번");
        seat.setSeatStatus(1L);
        model.addAttribute("seat", seat);
        return "menu"; // templates/menu.html
    }
}
