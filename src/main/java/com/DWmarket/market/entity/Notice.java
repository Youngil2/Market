package com.DWmarket.market.entity;

import com.DWmarket.market.constant.Role;
import com.DWmarket.market.dto.MemberFormDto;
import com.DWmarket.market.dto.NoticeFormDto;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name = "notice")
@Getter
@Setter
public class Notice extends BaseEntity{
    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; //제목

    private String subTitle; //부제목

    private String content; //내용

    @Enumerated(EnumType.STRING)
    private Role role;



    //@Builder //공지사항 부분
    public Notice notice(NoticeFormDto noticeFormDto){
        Notice notice = new Notice();
        notice.setId(noticeFormDto.getId());
        notice.setTitle(noticeFormDto.getTitle());
        notice.setContent(noticeFormDto.getContent());
        notice.setSubTitle(noticeFormDto.getSubTitle());
        notice.setRole(Role.Admin);


        return notice;
    }

    public void updateBoard(NoticeFormDto noticeFormDto){   //공지사항 부분
        this.title = noticeFormDto.getTitle();
        this.subTitle = noticeFormDto.getSubTitle();
        this.content = noticeFormDto.getContent();
    }






}
