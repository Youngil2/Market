package com.DWmarket.market.Repository;

import com.DWmarket.market.dto.MemberSearchDto;
import com.DWmarket.market.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MemberUser {

    Page<Member> getUserListPage(MemberSearchDto memberSearchDto, Pageable pageable);
}
