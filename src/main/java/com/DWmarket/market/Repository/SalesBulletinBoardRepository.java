package com.DWmarket.market.Repository;

import com.DWmarket.market.dto.ItemSearchDto;
import com.DWmarket.market.entity.SalesBulletinBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SalesBulletinBoardRepository extends JpaRepository<SalesBulletinBoard, Long>, QuerydslPredicateExecutor<SalesBulletinBoard>, SalesBulletinBoardRepositoryUser {

    List<SalesBulletinBoard>  findByItemNm(String itemNm);

    void deleteById(Long boardId);



}
