package com.DWmarket.market.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {
    private Long id;



    private String itemNm;
    private String detail;

    private String imgUrl;

    private Integer price;
    @QueryProjection    // Querydls로 결과 조회시 해당 객체로 받아오기 위함
    public MainItemDto(Long id, String itemNm, String detail, String imgUrl, Integer price){
        this.id=id;
        this.detail = detail;
        this.itemNm=itemNm;
        this.imgUrl=imgUrl;
        this.price=price;

    }
}
