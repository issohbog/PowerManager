package com.aloha.magicpos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.magicpos.domain.UserTickets;

@Mapper
public interface UserTicketMapper {
    
    // 이용권 등록 (결제 시 호출)
    int insert(UserTickets userTicket);

    // 전체 이용권 내역 조회 (관리자용)
    List<UserTickets> selectAll();

    // 특정 사용자 이용권 내역 조회
    List<UserTickets> findByUserNo(@Param("uNo") long uNo);

    // 특정 사용자 최근 남은 시간 조회
    Integer findRemainTimeByUserNo(@Param("uNo") long uNo);
}
