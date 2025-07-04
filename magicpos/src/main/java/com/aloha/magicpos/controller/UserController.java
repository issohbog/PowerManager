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
import com.aloha.magicpos.service.AuthService;
import com.aloha.magicpos.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;
    
    // ✅ 전체 회원 목록
    @GetMapping("/admin/userlist")
    public String list(
        @RequestParam(value = "type", required = false) String type, 
        @RequestParam(value = "keyword", required = false) String keyword, 
        Model model
        ) throws Exception {

        List<Users> userList = userService.searchUsers(type, keyword);
        model.addAttribute("users", userList);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);

        return "pages/admin/admin_user_list";
    }

    // ✅ 회원 등록 폼(사용안함)
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("user", new Users());
        return "user/form";
    }

    // ✅ 회원 등록 처리
    @PostMapping("/save")
    public String insert(Users user) throws Exception {
        userService.insert(user);

        // 기본 권한 부여 (예: ROLE_USER)
        Auths auth = new Auths();
        auth.setUNo(user.getNo());
        auth.setAuth("ROLE_USER");
        authService.insert(auth);

        return "redirect:/users/admin/userlist";
    }

    // ✅ 회원 수정 폼
    @GetMapping("/{no}/edit")
    public String edit(@PathVariable Long no, Model model) throws Exception {
        Users user = userService.selectById(no);
        model.addAttribute("user", user);
        return "user/form";
    }

    // ✅ 회원 수정 처리
    @PostMapping("/update")
    public String update(Users user) throws Exception {
        
        userService.update(user);
        return "redirect:/users/admin/userlist";
    }

    // ✅ 회원 삭제
    @PostMapping("/{no}/delete")
    public String delete(@PathVariable Long no) throws Exception {
        userService.delete(no);
        return "redirect:/users";
    }

    // ✅ 비밀번호 초기화 (관리자용)
    @PostMapping("/{no}/reset")
    public String resetPassword(@PathVariable Long no) throws Exception {
        String defaultPassword = "a123456789"; // ※ 필요시 암호화해서 넣기
        userService.resetPassword(no, defaultPassword);
        return "redirect:/users";
    }

    // ✅ 회원 검색
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) throws Exception {
        List<Users> users = userService.searchUsersByKeyword(keyword);
        model.addAttribute("users", users);
        return "user/list";
    }
}
