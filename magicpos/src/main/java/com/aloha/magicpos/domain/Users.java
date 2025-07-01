package com.aloha.magicpos.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class Users {
    private Long no;
    private String id;
    private String username;
    private String password;
    private Date birth;
    private String email;
    private String phone;
    private String memo;
    private Timestamp createdAt;
    private List<Auths> authList;
}
