package com.DWmarket.market.entity;

import com.DWmarket.market.dto.ReportDto;
import com.DWmarket.market.dto.SalesBulletinBoardFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "reportBoard")
@ToString
public class Report extends BaseEntity{

    @Id
    @Column(name = "reportId")
    @GeneratedValue
    private Long id;

    private String title;

    private String detail;

    private String secret;

    private String category;

    public void  updateItem(ReportDto reportDto){
        this.title=reportDto.getTitle();
        this.detail=reportDto.getDetail();
        this.secret=reportDto.getSecret();
        this.category=reportDto.getCategory();



    }
}
