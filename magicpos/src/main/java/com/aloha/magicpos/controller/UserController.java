package com.aloha.magicpos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.domain.Auths;
import com.aloha.magicpos.domain.Users;
import com.aloha.magicpos.mapper.AuthMapper;
import com.aloha.magicpos.mapper.UserMapper;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthMapper authMapper;
    
    // ✅ 전체 회원 목록
    @GetMapping
    public String list(Model model) {
        List<Users> users = userMapper.selectAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    // ✅ 회원 등록 폼
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("user", new Users());
        return "user/form";
    }

    // ✅ 회원 등록 처리
    @PostMapping
    public String insert(Users user) {
        userMapper.insert(user);

        // 기본 권한 부여 (예: ROLE_USER)
        Auths auth = new Auths();
        auth.setUNo(user.getNo());
        auth.setAuth("ROLE_USER");
        authMapper.insert(auth);

        return "redirect:/users";
    }

    // ✅ 회원 수정 폼
    @GetMapping("/{no}/edit")
    public String edit(@PathVariable Long no, Model model) {
        Users user = userMapper.selectById(no);
        model.addAttribute("user", user);
        return "user/form";
    }

    // ✅ 회원 수정 처리
    @PostMapping("/{no}")
    public String update(@PathVariable Long no, Users user) {
        user.setNo(no);
        userMapper.update(user);
        return "redirect:/users";
    }

    // ✅ 회원 삭제
    @PostMapping("/{no}/delete")
    public String delete(@PathVariable Long no) {
        userMapper.delete(no);
        return "redirect:/users";
    }

    // ✅ 비밀번호 초기화 (관리자용)
    @PostMapping("/{no}/reset")
    public String resetPassword(@PathVariable Long no) {
        String defaultPassword = "a123456789"; // ※ 필요시 암호화해서 넣기
        userMapper.resetPassword(no, defaultPassword);
        return "redirect:/users";
    }

    // ✅ 회원 검색
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Users> users = userMapper.searchUsersByKeyword(keyword);
        model.addAttribute("users", users);
        return "user/list";
    }
}
