package com.DWmarket.market.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SalesBulletinBoardImg")
@Getter
@Setter
public class SalesBulletinBoardImg extends BaseEntity{

    @Id
    @Column(name ="board_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;

    private String oriImgName;

    private String mainImgYn;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private SalesBulletinBoard board;

    public void updateItemImg(String ori, String img, String url){
        this.oriImgName= ori;
        this.imgName = img;
        this.imgUrl= url;
    }
}
