package com.DWmarket.market.config;

import com.DWmarket.market.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")   // 로그인 실패시?
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");   // 로그인 성공하면 메인 페이지로 가라



        http.authorizeRequests()
                .mvcMatchers("/","favicon.ico", "/members/**", "/board/**", "/images/**","/image/**","members/pwchange/**","/hns/**")
                .permitAll().mvcMatchers("/admin/**").hasRole("Admin")
                .anyRequest().authenticated();

        http.exceptionHandling().accessDeniedPage("/main");
        // 인증되지 않은 사용자가 접근할 경우 수행되는 작업

        http.csrf().ignoringAntMatchers("/members/pwchange/**")//csrf예외처리
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());//csrf 토큰자동생성

    }



    // 상품 등록관련
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/image/**","/error");
        // static 디렉토리 하위 파일에 대해 인증 없이 접근 가능하게 설정
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
