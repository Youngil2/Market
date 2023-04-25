package com.DWmarket.market.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainReportDto {
    private Long id;
    private String title;
    private String content;

    private String imgUrl;

    private String secret;

    @QueryProjection
    public MainReportDto(Long id, String title, String content, String imgUrl, String secret){
        this.id=id;
        this.title=title;
        this.content=content;
        this.imgUrl=imgUrl;
        this.secret=secret;
    }
}
