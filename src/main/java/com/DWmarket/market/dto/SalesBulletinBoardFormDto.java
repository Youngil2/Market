package com.DWmarket.market.dto;

import com.DWmarket.market.constant.ItemSellStatus;
import com.DWmarket.market.entity.Member;
import com.DWmarket.market.entity.SalesBulletinBoard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SalesBulletinBoardFormDto {
    private Long id;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "상품명은 필수 입력입니다.")
    private String itemNm;

    private Integer price;

    @NotBlank(message = "상품설명은 필수 입력입니다.")
    private String detail;

    private String category;



    private ItemSellStatus itemSellStatus;

    private List<SalesBulletinBoardImgDto> salesBulletinBoardImgDtoList = new ArrayList<>();//객체가 있기에 null이 아니냐고 보단 itemImgDtoList에 있냐(1) 없냐(0)로 구분해야한다.

    private List<Long> itemImgIds = new ArrayList<>();
    private String createBy;
    private static ModelMapper modelMapper = new ModelMapper();

    public SalesBulletinBoard createItem(){
        SalesBulletinBoard salesBulletinBoard= new SalesBulletinBoard();
        return modelMapper.map(this,SalesBulletinBoard.class);
    }
    public static SalesBulletinBoardFormDto of(SalesBulletinBoard salesBulletinBoard){
        return modelMapper.map(salesBulletinBoard, SalesBulletinBoardFormDto.class);
    } // entit를 Dto로 바꿔주는 거 단 entity와 변수이름이 같아야 함.
}
