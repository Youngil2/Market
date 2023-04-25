package com.DWmarket.market.entity;

import com.DWmarket.market.constant.ItemSellStatus;
import com.DWmarket.market.dto.MemberFormDto;
import com.DWmarket.market.dto.SalesBulletinBoardFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "salesBulletinBoard")
@Getter
@Setter
@ToString
public class SalesBulletinBoard extends BaseEntity{
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 50)
    private String itemNm;

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String detail;

    private String category;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;




    public void  updateItem(SalesBulletinBoardFormDto board){
        this.title=board.getTitle();
        this.itemNm=board.getItemNm();
        this.price = board.getPrice();
        this.category=board.getCategory();
        this.detail = board.getDetail();
        this.itemSellStatus=board.getItemSellStatus();

    }
}
