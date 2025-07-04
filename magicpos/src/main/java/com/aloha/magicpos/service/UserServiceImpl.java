package com.aloha.magicpos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.domain.Users;
import com.aloha.magicpos.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired private UserMapper userMapper;

    @Override
    public List<Users> selectAll() throws Exception {
        return userMapper.selectAll();
    }

    @Override
    public List<Users> searchUsers(String type, String keyword) {
        // 전체보기 (검색 조건 없을 때)
        if (keyword == null || keyword.isBlank() || type == null || type.isBlank()) {
            return userMapper.selectAll();
        }

        // 검색어 있을 경우 
        return userMapper.searchBy(type, keyword);
    }


    @Override
    public Users selectById(long no) throws Exception {  
        return userMapper.selectById(no);
    }

    @Override
    public Users findById(String id) throws Exception {
        return userMapper.findById(id);
    }

    @Override
    public boolean insert(Users user) throws Exception {
        return userMapper.insert(user) > 0; 
    }

    @Override
    public boolean update(Users user) throws Exception {
        return userMapper.update(user) > 0;
    }

    @Override
    public boolean resetPassword(long no, String defaultPassword) throws Exception {
        return userMapper.resetPassword(no, defaultPassword) > 0;
    }

    @Override
    public boolean updateUserProfile(Users user) throws Exception {
        return userMapper.updateUserProfile(user) > 0;
    }

    @Override
    public boolean delete(long no) throws Exception {
        return userMapper.delete(no) > 0;
    }

    @Override
    public List<Users> searchUsersByKeyword(String keyword) throws Exception {
        return userMapper.searchUsersByKeyword(keyword);
    }



    
}
