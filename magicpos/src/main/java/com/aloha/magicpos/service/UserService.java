package com.aloha.magicpos.service;

import java.util.List;

import com.aloha.magicpos.domain.Users;

public interface UserService {
    // 회원 전체 조회
    public List<Users> selectAll() throws Exception;

    // 단일 회원 조회 (번호 기준)
    public Users selectByNo(long no) throws Exception;

    // 아이디로 회원 조회
    public Users findById(String id) throws Exception;

    // 회원 등록
    public boolean insert(Users user) throws Exception;

    // 관리자용 회원 정보 수정
    public boolean update(Users user) throws Exception;

    // 관리자용 비밀번호 초기화
    public boolean resetPassword(long no, String defaultPassword) throws Exception;

    // 사용자용 회원 정보 수정
    public boolean updateUserProfile(Users user) throws Exception;

    // 회원 탈퇴
    public boolean delete(long no) throws Exception;

    // 회원 검색 (이름 / 아이디 / 전화번호)
    public List<Users> searchUsersByKeyword(String keyword) throws Exception;
}
