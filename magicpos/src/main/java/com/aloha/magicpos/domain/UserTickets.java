package com.aloha.magicpos.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserTickets {
    private Long no;
    private Long uNo;
    private Long tNo;
    private Long remainTime;
    private Timestamp payAt;
}
