package com.DWmarket.market.dto;

import com.DWmarket.market.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchDto {
    private String searchDataType;  // 조회 요청 현재 시간 -  요청시간 기준으로 상품조회

    private String searchBy;  // 상품 조회 유형 -  상품명, 상품 등록자 등등
    private String searchQuery=""; // 데이터베이스 조회 조건
}
