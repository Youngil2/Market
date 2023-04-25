package com.DWmarket.market.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SalesBulletinBoardDto {
    private Long id;

    private String itemNm;

    private Integer price;

    private String itemDetail;

    private LocalDateTime regTime;


    private Integer likeCount;

    private LocalDateTime updateTime;




}
