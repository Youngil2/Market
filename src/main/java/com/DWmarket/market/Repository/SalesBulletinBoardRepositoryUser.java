package com.DWmarket.market.Repository;

import com.DWmarket.market.dto.ItemSearchDto;
import com.DWmarket.market.dto.MainItemDto;
import com.DWmarket.market.entity.SalesBulletinBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SalesBulletinBoardRepositoryUser {

    Page<SalesBulletinBoard> getAdminItemPage (ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> findByCategoryDiv(String category, Pageable pageable,ItemSearchDto itemSearchDto);
}
