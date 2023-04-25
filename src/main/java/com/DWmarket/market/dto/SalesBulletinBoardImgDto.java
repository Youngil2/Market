package com.DWmarket.market.dto;

import com.DWmarket.market.entity.SalesBulletinBoardImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class SalesBulletinBoardImgDto {
    private Long id;
    private String oriImgName;
    private String imgName;
    private String imgUrl;
    private String mainImgYn;
    private static ModelMapper modelMapper = new ModelMapper();

    public static SalesBulletinBoardImgDto of(SalesBulletinBoardImg salesBulletinBoardImg) {
        return modelMapper.map(salesBulletinBoardImg, SalesBulletinBoardImgDto.class);

    }
}
