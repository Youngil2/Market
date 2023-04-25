package com.DWmarket.market.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authenticaion = SecurityContextHolder.getContext().getAuthentication();
        String userid = "";
        if(authenticaion != null){
            userid = authenticaion.getName(); //현재로그인한 회원의 정보를 조회하여 사용자 이름을 등록자와 수정자로 지정
        }
        return Optional.of(userid);
    }
}
