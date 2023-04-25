package com.DWmarket.market.Repository;

import com.DWmarket.market.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, MemberUser {
    Member findByEmail(String email);
    Member findByNameAndPhone(String name, String tel);

    Member findByNameAndEmail(String name, String email);

    Member findByPassword(String password);

    void deleteByEmail(String email); // service 값에 return값이 없기때문에 void로 잡아줌.

    Member findByName(String name); //mypage에 이름값 자동넣기
}
