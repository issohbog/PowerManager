package com.aloha.magicpos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.magicpos.domain.Logs;

@Mapper
public interface LogMapper {
    // 로그 등록
    int insert(Logs logs);

    // 전체 로그 조회
    List<Logs> findAll();

    // 유저 번호로 조회
    List<Logs> findByUserNo(Long uNo);

    // 이름(username)으로 조회
    List<Logs> findByUsername(String username);

    // 아이디(id)로 조회
    List<Logs> findByUserId(String id);

    // 좌석번호로 조회
    List<Logs> findBySeatId(String seatId);

    // 로그 유형별 조회
    List<Logs> findByActionType(String actionType);

    // 로그 유형 + 이름/아이디/좌석번호로 검색 (일치 조건)
    List<Logs> searchLogsByActionTypeAndKeyword(
        @Param("actionType") String actionType,
        @Param("keyword") String keyword
    );
}
