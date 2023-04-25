package com.DWmarket.market.entity;

import com.DWmarket.market.constant.Gender;
import com.DWmarket.market.constant.Role;
import com.DWmarket.market.dto.MemberFormDto;
import com.DWmarket.market.dto.PassFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity{
    @Id
    @Column(name = "memberId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String addr;

    private String password;

    private String interest;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;



    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddr(memberFormDto.getAddr());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setInterest(memberFormDto.getInterest());
        member.setPhone(memberFormDto.getPhone());
        member.setGender(memberFormDto.getGender());
        member.setRole(Role.User);


        return member;
    }

    public void memberUpdate(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        this.name=memberFormDto.getName();
        this.addr=memberFormDto.getAddr();
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        this.password=password;
        this.interest=memberFormDto.getInterest();
        this.phone=memberFormDto.getPhone();
        this.gender=memberFormDto.getGender();

    }

    public void memberPasswordModify(PassFormDto passFormDto, PasswordEncoder passwordEncoder){
        String password = passwordEncoder.encode(passFormDto.getPassword());
        this.password=password;
    }
}
