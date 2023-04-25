package com.DWmarket.market.dto;

import com.DWmarket.market.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PassFormDto {
    @NotEmpty(message="비밀번호는 필수 입력값입니다.")
    @Length(min=8, max=20)
    @Pattern(regexp="(?=.*[0-9])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;


    @Length(min=8, max=20, message="비밀번호를 다릅니다.")
    private String pass;

    private static ModelMapper modelMapper = new ModelMapper();
    public static PassFormDto of(Member member){
        return modelMapper.map(member, PassFormDto.class);
    }
}
