package com.aloha.magicpos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.domain.Tickets;
import com.aloha.magicpos.domain.UserTickets;
import com.aloha.magicpos.mapper.UserTicketMapper;
import com.aloha.magicpos.service.TicketService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("UserTicketService")
public class UserTicketServiceImpl implements UserTicketService {

    @Autowired UserTicketMapper userTicketMapper;
    
    @Autowired TicketService ticketService;


    @Override
    public boolean insert(UserTickets userTicket) throws Exception {
        return userTicketMapper.insert(userTicket) > 0;
    }

    @Override
    public List<UserTickets> selectAll() throws Exception {
        return userTicketMapper.selectAll();
    }

    @Override
    public List<UserTickets> findByUserNo(long uNo) throws Exception {
        return userTicketMapper.findByUserNo(uNo);
    }

    @Override
    public Integer findRemainTimeByUserNo(long uNo) throws Exception {
        return userTicketMapper.findRemainTimeByUserNo(uNo);
    }

    @Override
    public Long getTotalRemainTime(Long userNo) {
        return userTicketMapper.subRemainTimeByUser(userNo);
    }
    
    @Override
    public boolean insertUserTicketByAdmin(UserTickets userTicket) throws Exception {
        log.info("🎫 서비스에서 티켓 정보 조회 및 요금제 구매 처리");
        
        try {
            // 티켓 정보 조회
            Tickets ticket = ticketService.findById(userTicket.getTNo());
            if (ticket != null) {
                userTicket.setRemainTime(ticket.getTime()); // 티켓의 시간을 remainTime으로 설정
                log.info("🎫 티켓 정보 조회 완료: {}분", ticket.getTime());
            } else {
                log.error("🔥 티켓을 찾을 수 없습니다: tNo = {}", userTicket.getTNo());
                return false;
            }
        } catch (Exception e) {
            log.error("🔥 티켓 정보 조회 중 오류: {}", e.getMessage());
            return false;
        }
        
        // 요금제 구매 처리
        return userTicketMapper.insert(userTicket) > 0;
    }
    
}
