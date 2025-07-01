package com.aloha.magicpos.domain;

import lombok.Data;

@Data
public class Products {
    private Long no;
    private Long cNo;
    private String pName;
    private Long pPrice;
    private String imgPath;
    private String description;
    private Boolean sellStatus;
    private Long stock;
}
