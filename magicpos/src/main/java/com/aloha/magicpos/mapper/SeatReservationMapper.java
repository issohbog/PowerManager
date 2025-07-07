package com.aloha.magicpos.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeatReservationMapper {
    Long getTotalUsedTime(Long userNo);
    
} 