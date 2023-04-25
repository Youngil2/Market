package com.DWmarket.market.Repository;

import com.DWmarket.market.entity.ReportImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportImgRepository extends JpaRepository<ReportImg, Long> {

    List<ReportImg> findByReportIdOrderByIdAsc(Long reportId);

    void deleteByReportId(Long reportId);

    void  deleteById(Long reportId);
}
