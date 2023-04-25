package com.DWmarket.market.Repository;

import com.DWmarket.market.dto.MainReportDto;
import com.DWmarket.market.dto.ReportSearchDto;
import com.DWmarket.market.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportRepositoryUser {
    Page<Report> getAdminPage(ReportSearchDto reportSearchDto, Pageable pageable);
    Page<MainReportDto> getReportPage(ReportSearchDto reportSearchDto, Pageable pageable);
}
