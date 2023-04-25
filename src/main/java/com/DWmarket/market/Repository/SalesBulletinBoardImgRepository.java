package com.DWmarket.market.Repository;

import com.DWmarket.market.entity.SalesBulletinBoardImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesBulletinBoardImgRepository extends JpaRepository<SalesBulletinBoardImg, Long> {
    //List<SalesBulletinBoardImg>  findAllById(Long boardId);

    List<SalesBulletinBoardImg>  findByBoardIdOrderByIdAsc(Long boardId);

    void  deleteById(Long boardId);


    void deleteByBoardId(Long boardId);
}

