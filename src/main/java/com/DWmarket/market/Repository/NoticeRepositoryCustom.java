package com.DWmarket.market.Repository;

import com.DWmarket.market.dto.NoticeSearchDto;
import com.DWmarket.market.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable);
}
