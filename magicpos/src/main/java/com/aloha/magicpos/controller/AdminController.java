package com.aloha.magicpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class AdminController {
    @GetMapping("/admin")
    public String getMethodName(Model model) {
        if (model.getAttribute("seats") == null) {
            List<Map<String, Object>> seats = new ArrayList<>();
            for (int i = 1; i <= 43; i++) {
                Map<String, Object> seat = new HashMap<>();
                seat.put("no", i);

                // 예시로 상태를 순환: available, in-use, cleaning, broken
                String[] statuses = {"available", "in-use", "cleaning", "broken"};
                String status = statuses[i % statuses.length];
                seat.put("status", status);

                boolean isInUse = status.equals("in-use");
                int timeLeft = isInUse ? i * 5 : 0;
                String userName = isInUse ? "User" + i : null;

                seat.put("timeLeft", timeLeft);
                seat.put("userName", userName);


                // ✅ className 결정
                String className = switch (status) {
                    case "broken" -> "broken";
                    case "cleaning" -> "cleaning";
                    case "in-use" -> (timeLeft > 60 ? "in-use-green" : "in-use-red");
                    default -> "available";
                };
                
                seat.put("className", className);
                
                seats.add(seat);
            }
            model.addAttribute("seats", seats);
        }
        return "pages/admin/seat_status";
    }
    
}
    

