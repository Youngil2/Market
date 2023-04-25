package com.DWmarket.market.service;

import com.DWmarket.market.Repository.MemberRepository;
import com.DWmarket.market.dto.MemberFormDto;
import com.DWmarket.market.dto.MemberSearchDto;
import com.DWmarket.market.dto.PassFormDto;
import com.DWmarket.market.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository mrep;

    private final MailService mailService;





    public Member saveMember(Member mem){
        validatedup(mem);

        return mrep.save(mem);
    }

    private void validatedup(Member member){
        Member findm = mrep.findByEmail(member.getEmail());
        if( findm !=null ){
            throw new IllegalStateException("이미 가입된 이메일 입니다.");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = mrep.findByEmail(email);
        if(member ==null){  //이메일 조회 실패
            throw new UsernameNotFoundException(email);
        }
        return User.builder().username(member.getEmail()).password(member.getPassword()).roles(member.getRole().toString()).build();
    }

    public void memberUpdate(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = mrep.findByEmail(memberFormDto.getEmail());
        member.memberUpdate(memberFormDto,passwordEncoder);

    }

    public void passwordUpdate(PassFormDto passFormDto, String email,  PasswordEncoder passwordEncoder ){
        Member member = mrep.findByEmail(email);
        MemberFormDto memberFormDto = MemberFormDto.of(member); //변경할 이메일의 member DB 값 가져와서 MemberFormDto객체로 변경
        memberFormDto.setPassword(passFormDto.getPassword()); //MemberFormDto객체에 변경하 패스워드 저장
        member.memberUpdate(memberFormDto,passwordEncoder); // MemberformDto객체의 값을 member 객체로 변경하면서 update
    }

    @Transactional(readOnly = true) // 비밀번호 찾기
    public void getpassUpdate(PassFormDto passFormDto, String email, PasswordEncoder passwordEncoder){
        Member member = mrep.findByEmail(email);

        member.memberPasswordModify(passFormDto,passwordEncoder);


    }

    @Transactional(readOnly = true)
    public Page<Member> getUserListPage(MemberSearchDto memberSearchDto, Pageable pageable){
        return mrep.getUserListPage(memberSearchDto, pageable);
    }

    @Transactional(readOnly = true) //아이디 찾기
    public MemberFormDto getMemberInformation(String email){
        Member member = mrep.findByEmail(email);
        MemberFormDto memberFormDto = MemberFormDto.of(member);
        return memberFormDto;
    }

    public String searchId(String name, String tel){
        String phone="";
        if ( tel.length()  ==11){  // 연락처번호 길이가 11이라면
            phone = tel.substring(0,3)+"-"+tel.substring(3,7)+"-"+tel.substring(7);
        }
        Member member = mrep.findByNameAndPhone(name,phone);
        return member.getEmail();
    }


    public void searchPw(String name, String email, String sendEmail){


        Member member = mrep.findByNameAndEmail(name,email);

        String link = "http://localhost:80/members/pwchange/"+email;
        mailService.sendMail(sendEmail,link,email);

    }

    public String modifyPassword(Member member, PasswordEncoder passwordEncoder, String password){
        member = mrep.findByPassword(password);
        return member.getPassword();
    }

    // 회원탈퇴

    public void delete(String email) {
        mrep.deleteByEmail(email);

    }


    public String ViewName(String email) { //이름 확인
        Member member = mrep.findByEmail(email);

        return member.getName();
    }
}
