package com.DWmarket.market.Repository;

import com.DWmarket.market.constant.Role;
import com.DWmarket.market.dto.MemberSearchDto;
import com.DWmarket.market.entity.Member;
import com.DWmarket.market.entity.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class MemberUserImpl implements MemberUser{
    private JPAQueryFactory queryFactory;

    public MemberUserImpl(EntityManager em){
        this.queryFactory= new JPAQueryFactory(em);
    }
    public BooleanExpression searchByLike(String searchQuery) {

        return QMember.member.email.like("%"+searchQuery+"%");


    }
    public BooleanExpression  searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("itemNm", searchBy)){ //상품명 이나 등록자에 있으면 조회
            return QMember.member.email.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("createdBy", searchBy)){
            return QMember.member.createBy.like("%"+searchQuery+"%");
        }
        return null;
        //searchBy의 값에 따라 상품 또는 상품 등록자의 아이디에 검색어가 포함되어있는 상품을 조회
    }

    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();
        if (StringUtils.equals("all" , searchDateType)|| searchDateType==null){
            return null;
        }else if (StringUtils.equals("1d" , searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("1w" , searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("1m" , searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("6m" , searchDateType)){
            dateTime = dateTime.minusDays(6);
        }
        return QMember.member.regTime.after(dateTime);
    }
    private BooleanExpression Role(){

        return QMember.member.role.eq(Role.User);
    }
    @Override
    public Page<Member> getUserListPage(MemberSearchDto memberSearchDto, Pageable pageable) {
        QueryResults<Member> results = queryFactory.selectFrom(QMember.member)
                .where(regDtsAfter(memberSearchDto.getSearchDataType()),
                        searchByLike(memberSearchDto.getSearchBy(),
                                memberSearchDto.getSearchQuery()))
                .where(searchByLike(memberSearchDto.getSearchQuery()),Role() )
                .orderBy(QMember.member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Member> content = results.getResults();
        Long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
