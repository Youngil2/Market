package com.DWmarket.market.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "reportImg")
@Getter
@Setter
public class ReportImg extends BaseEntity{
    @Id
    @Column(name = "reportImgId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;

    private String oriImgName;

    private String mainImgYn;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportId")
    private Report report;

    public void updateItemImg(String ori, String img, String url){
        this.oriImgName= ori;
        this.imgName = img;
        this.imgUrl= url;
    }



}
