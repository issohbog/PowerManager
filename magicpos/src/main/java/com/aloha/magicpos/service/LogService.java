package com.aloha.magicpos.service;

import java.util.List;

import com.aloha.magicpos.domain.Logs;

public interface LogService {
    public boolean insert(Logs logs) throws Exception;
    public List<Logs> findAll() throws Exception;
    public List<Logs> findByUserNo(Long uNo) throws Exception;
    public List<Logs> findByUsername(String username) throws Exception;
    public List<Logs> findByUserId(String id) throws Exception;
    public List<Logs> findBySeatId(String seatId) throws Exception;
    public List<Logs> findByActionType(String actionType) throws Exception;
    public List<Logs> searchLogsByActionTypeAndKeyword(String actionType,String keyword) throws Exception;

}
