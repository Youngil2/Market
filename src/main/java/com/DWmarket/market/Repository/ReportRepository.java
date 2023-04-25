package com.DWmarket.market.Repository;

import com.DWmarket.market.dto.ReportSearchDto;
import com.DWmarket.market.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long>, QuerydslPredicateExecutor<Report>, ReportRepositoryUser {

    List<Report> findByTitle(String title);

    void deleteById(Long reportId);
}
