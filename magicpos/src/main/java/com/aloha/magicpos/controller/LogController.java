package com.aloha.magicpos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.domain.Logs;
import com.aloha.magicpos.mapper.LogMapper;

@Controller
@RequestMapping("/logs")
public class LogController {
    @Autowired
    private LogMapper logMapper;

    // 🔸 로그 등록
    @PostMapping
    public String insert(@RequestBody Logs logs) {
        logMapper.insert(logs);
        return "created";
    }

    // 🔹 전체 로그 조회
    @GetMapping
    public List<Logs> findAll() {
        return logMapper.findAll();
    }

    // 🔹 유저 번호로 조회
    @GetMapping("/user-no/{uNo}")
    public List<Logs> findByUserNo(@PathVariable Long uNo) {
        return logMapper.findByUserNo(uNo);
    }

    // 🔹 사용자 이름으로 조회
    @GetMapping("/username")
    public List<Logs> findByUsername(@RequestParam String username) {
        return logMapper.findByUsername(username);
    }

    // 🔹 사용자 아이디로 조회
    @GetMapping("/user-id")
    public List<Logs> findByUserId(@RequestParam String id) {
        return logMapper.findByUserId(id);
    }

    // 🔹 좌석 아이디로 조회
    @GetMapping("/seat-id")
    public List<Logs> findBySeatId(@RequestParam String seatId) {
        return logMapper.findBySeatId(seatId);
    }

    // 🔹 로그 액션 타입으로 조회
    @GetMapping("/action-type")
    public List<Logs> findByActionType(@RequestParam String actionType) {
        return logMapper.findByActionType(actionType);
    }

    // 🔹 로그 액션타입 + 키워드 검색
    @GetMapping("/search")
    public List<Logs> searchLogsByActionTypeAndKeyword(
            @RequestParam String actionType,
            @RequestParam String keyword
    ) {
        return logMapper.searchLogsByActionTypeAndKeyword(actionType, keyword);
    }
}
