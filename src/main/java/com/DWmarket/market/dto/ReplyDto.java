package com.DWmarket.market.dto;

import com.DWmarket.market.entity.Reply;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyDto {
    private Long id;

    String content;
    private String createBy;
    private String name;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    //private static ModelMapper modelMapper = new ModelMapper();
    public static ReplyDto of(Reply reply){   // 맵퍼 안써!!!    갯수도 얼마 없고  엔티티와 다른 변수도 존재하기때문에 안써!
        ReplyDto replyDto = new ReplyDto(); // Dto 객체 만들기
        replyDto.setId(reply.getId());  //엔티티 ID 가져오기
        replyDto.setName(reply.getUser().getName()); // 엔티티에 있는 작성자(Member 엔티티에서 name 값)가져오기
        replyDto.setContent(reply.getContent()); // 엔티티에서  content 값 가져오기
        replyDto.setRegTime(reply.getRegTime());
        replyDto.setUpdateTime(reply.getUpdateTime());
        return replyDto;
    }
}
