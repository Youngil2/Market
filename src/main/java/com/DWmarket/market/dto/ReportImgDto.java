package com.DWmarket.market.dto;

import com.DWmarket.market.entity.ReportImg;
import com.DWmarket.market.entity.SalesBulletinBoardImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ReportImgDto {
    private Long id;
    private String oriImgName;
    private String imgName;
    private String imgUrl;
    private String mainImgYn;
    private static ModelMapper modelMapper = new ModelMapper();

    public static ReportImgDto of(ReportImg reportImg) {
        return modelMapper.map(reportImg, ReportImgDto.class);

    }
}
