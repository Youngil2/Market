package com.DWmarket.market.Repository;

import com.DWmarket.market.dto.NoticeSearchDto;
import com.DWmarket.market.entity.Notice;
import com.DWmarket.market.entity.QNotice;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom{
    private JPAQueryFactory queryFactory;   //JPA 의 동적 쿼리를 사용할때 사용하는 클래스객체

    public  NoticeRepositoryCustomImpl(EntityManager em){
        this.queryFactory= new JPAQueryFactory(em);
    }

    public BooleanExpression searchByLike(String searchQuery) {

        return QNotice.notice.title.like("%"+searchQuery+"%");

        // 검색어가 포함되어있는 공지사항을 조회
    }
    @Override
    public Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable) {
        QueryResults<Notice> results = queryFactory.selectFrom(QNotice.notice)// 쿼리리절트를 노티스 타입으로 변수 생성, 리절트라는 변수에 쿼리문 실행한 정보가 담긴다.
                .where(searchByLike(noticeSearchDto.getSearchQuery())) // 검색 조건
                .orderBy(QNotice.notice.id.desc()) // notice.id를 내림차순으로 정렬
                .offset(pageable.getOffset()) //페이지 정보
                .limit(pageable.getPageSize()) //페이지 정보
                .fetchResults(); //실행
        List<Notice> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
}
