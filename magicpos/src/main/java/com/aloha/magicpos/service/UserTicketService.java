package com.aloha.magicpos.service;

import java.util.List;

import com.aloha.magicpos.domain.UserTickets;

public interface UserTicketService {
    // 이용권 등록 (결제 시 호출)
    public boolean insert(UserTickets userTicket) throws Exception;

    // 전체 이용권 내역 조회 (관리자용)
    public List<UserTickets> selectAll() throws Exception;

    // 특정 사용자 이용권 내역 조회
    public List<UserTickets> findByUserNo(long uNo) throws Exception;

    // 특정 사용자 최근 남은 시간 조회
    public Integer findRemainTimeByUserNo(long uNo) throws Exception;
}
