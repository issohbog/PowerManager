package com.aloha.magicpos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.domain.UserTickets;
import com.aloha.magicpos.mapper.UserTicketMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("UserTicketService")
public class UserTicketServiceImpl implements UserTicketService {

    @Autowired UserTicketMapper userTicketMapper;


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
    
}
