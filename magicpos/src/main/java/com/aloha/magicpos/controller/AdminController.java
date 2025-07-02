package com.aloha.magicpos.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class AdminController {
    @GetMapping("/admin")
    public String getMethodName() {
        return "admin_header";
    }
    
}
