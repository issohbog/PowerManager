package com.aloha.magicpos.domain;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    private List<Auths> authList;
}
