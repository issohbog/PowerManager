package com.aloha.magicpos.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SeatsReservations {
    private Long no;
    private String seatId;
    private Long uNo;
    private Timestamp startTime;
    private Timestamp endTime;
}
