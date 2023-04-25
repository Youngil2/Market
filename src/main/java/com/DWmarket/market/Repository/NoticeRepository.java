package com.DWmarket.market.Repository;

import com.DWmarket.market.constant.Role;
import com.DWmarket.market.entity.Member;
import com.DWmarket.market.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;



public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Notice>, NoticeRepositoryCustom{
    //Notice findByAdmin(Member admin);



    void deleteById(Long boardId);

    Notice findByTitle(String title);


}
