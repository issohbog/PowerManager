package com.aloha.magicpos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.domain.Users;
import com.aloha.magicpos.mapper.UserMapper;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    
    // 🔐 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login"; // ex) templates/auth/login.html
    }

    // 🔐 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String id,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Users user = userMapper.findById(id);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "auth/login";
        }

        // 로그인 성공 → 세션 저장
        session.setAttribute("loginUser", user);
        return "redirect:/"; // 로그인 후 메인 페이지로
    }

    // 🔓 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login?logout";
    }
}
