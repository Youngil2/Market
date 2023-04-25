package com.DWmarket.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "reply")
@ToString
public class Reply  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "replyId")
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 120)
    String content;

    @ManyToOne
    @JoinColumn(name="board_id")
    private SalesBulletinBoard board;

    @ManyToOne
    @JoinColumn(name="memberId")
    private Member user;

}
