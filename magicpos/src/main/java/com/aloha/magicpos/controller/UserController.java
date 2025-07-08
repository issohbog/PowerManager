package com.aloha.magicpos.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.magicpos.domain.Auths;
import com.aloha.magicpos.domain.Users;
import com.aloha.magicpos.service.AuthService;
import com.aloha.magicpos.service.SeatReservationService;
import com.aloha.magicpos.service.UserService;
import com.aloha.magicpos.service.UserTicketService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired 
    private UserTicketService userTicketService;

    @Autowired
    private SeatReservationService seatReservationService;

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
        // 🔥 사용자별 사용시간/남은시간 계산
        for (Users user : userList) {
            Long remain = userTicketService.getTotalRemainTime(user.getNo());
            Long used = seatReservationService.getTotalUsedTime(user.getNo());

            user.setRemainMin(remain);  
            user.setUsedMin(used);      
        }

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


    // 아이디 중복 체크 
    @GetMapping("/admin/check-id")
    @ResponseBody                           // 컨트롤러 메소드의 반환값을 HTTP응답 body로 직접 전송 한다는 의미(뷰 이름 X)
    public Map<String, Boolean> getMethodName(@RequestParam("id") String id) {
        boolean exists = userService.isIdExist(id);
        return Collections.singletonMap("exists", exists);          // key, value 가 1쌍인 map
    }
    
    // 회원 등록 처리
    @PostMapping("/save")
    public String insert(Users user, Model model) throws Exception {
        // 임시비밀번호 생성 + 저장된 사용자 정보 반환
        Users savedUser = userService.insert(user);

        // 기본 권한 부여 (예: ROLE_USER)
        Auths auth = new Auths();
        auth.setUNo(user.getNo());
        auth.setAuth("ROLE_USER");
        authService.insert(auth);

        // 전체 회원 목록 가져오기 
        List<Users> allUsers = userService.selectAll();

        // 생성된 임시비밀번호 뷰에 전달 
        model.addAttribute("users", allUsers);
        model.addAttribute("savedUser", savedUser);
        
        // 기존 페이지에 임시 비번이 적힌 모달을 열기 위한 플래그 전달
        model.addAttribute("showSuccessModal", true);
        // 성공
        return "pages/admin/admin_user_list";
    }

    // ✅ 회원 수정 폼

    @GetMapping("/admin/{userNo}/info")
    @ResponseBody
    public Map<String, Object> getUserInfo(@PathVariable("userNo") Long userNo) throws Exception {
        System.out.println("userNo: " + userNo);

        Users user = userService.findByNo(userNo);
        System.out.println("user : " + user);
        Long remainTime = userTicketService.getTotalRemainTime(userNo);
        Long usedTime = seatReservationService.getTotalUsedTime(userNo);

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("remainTime", remainTime);
        result.put("usedTime", usedTime);

        return result;      // json응답

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
