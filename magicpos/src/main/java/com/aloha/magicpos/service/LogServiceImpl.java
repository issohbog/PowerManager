package com.aloha.magicpos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.domain.Logs;
import com.aloha.magicpos.mapper.LogMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("LogService")
public class LogServiceImpl implements LogService{
    @Autowired
    private LogMapper logMapper;

    @Override
    public boolean insert(Logs logs) throws Exception {
        return logMapper.insert(logs) >0;
    }

    @Override
    public List<Logs> findAll() throws Exception {
        return logMapper.findAll();
    }

    @Override
    public List<Logs> findByUserNo(Long uNo) throws Exception {
        return logMapper.findByUserNo(uNo);
    }

    @Override
    public List<Logs> findByUsername(String username) throws Exception {
        return logMapper.findByUsername(username);
    }

    @Override
    public List<Logs> findByUserId(String id) throws Exception {
        return logMapper.findByUserId(id);
    }

    @Override
    public List<Logs> findBySeatId(String seatId) throws Exception {
        return logMapper.findBySeatId(seatId);
    }

    @Override
    public List<Logs> findByActionType(String actionType) throws Exception {
        return logMapper.findByActionType(actionType);
    }

    @Override
    public List<Logs> searchLogsByActionTypeAndKeyword(String actionType, String keyword) throws Exception {
        return logMapper.searchLogsByActionTypeAndKeyword(actionType, keyword);
    }
    
}
