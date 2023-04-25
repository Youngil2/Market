package com.DWmarket.market.dto;

import com.DWmarket.market.constant.Role;
import com.DWmarket.market.entity.Notice;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeFormDto {
    private Long id;

    @NotBlank(message = "제목 입력은 필수입니다.")
    private String title;


    private String subTitle;

    @NotBlank(message = "내용 입력은 필수입니다.")
    private String content;
    private String createBy;
    private Role role;
    private LocalDateTime regTime;



    private static ModelMapper modelMapper = new ModelMapper();
    public static NoticeFormDto of(Notice notice){
        return modelMapper.map(notice, NoticeFormDto.class);
    }
}
