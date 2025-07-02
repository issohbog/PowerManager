package com.aloha.magicpos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.service.LoginService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;
    
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
                        Model model) throws Exception{
        boolean result = loginService.login(id, password, session, model);
        return result ? "redirect:/" : "auth/login";
    }

    // 🔓 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) throws Exception{
        loginService.logout(session);
        return "redirect:/auth/login?logout";
    }
}
