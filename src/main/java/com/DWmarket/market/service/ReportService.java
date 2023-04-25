package com.DWmarket.market.service;

import com.DWmarket.market.Repository.ReportImgRepository;
import com.DWmarket.market.Repository.ReportRepository;
import com.DWmarket.market.dto.MainReportDto;
import com.DWmarket.market.dto.ReportDto;
import com.DWmarket.market.dto.ReportImgDto;
import com.DWmarket.market.dto.ReportSearchDto;
import com.DWmarket.market.entity.Report;
import com.DWmarket.market.entity.ReportImg;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    private final ReportImgService reportImgService;

    private final ReportImgRepository reportImgRepository;

    public Long saveReport(ReportDto reportDto, List<MultipartFile> itemImgFileList) throws Exception{
        Report report_obj = reportDto.createItem();
        System.out.print(report_obj.getDetail()+"  "+report_obj.getSecret()+"   " +report_obj.getCategory());
        reportRepository.save(report_obj);
        for( int i=0; i<itemImgFileList.size(); i++  ){
            ReportImg reportImg = new ReportImg();
            reportImg.setReport(report_obj);
            if( i==0 )
                reportImg.setMainImgYn("Y");
            else
                reportImg.setMainImgYn("N");

            reportImgService.saveItemImg(reportImg, itemImgFileList.get(i));
        }
        return report_obj.getId();
    }
    @Transactional(readOnly = true)
    public Page<MainReportDto> getReportPage(ReportSearchDto reportSearchDto, Pageable pageable){
        return reportRepository.getReportPage(reportSearchDto, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Report> getAdminPage(ReportSearchDto reportSearchDto, Pageable pageable){
        return reportRepository.getAdminPage(reportSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public ReportDto getItemDtl(Long reportId){
        List<ReportImg> reportImgList = reportImgRepository.findByReportIdOrderByIdAsc(reportId);
        List<ReportImgDto> reportImgDtoList = new ArrayList<>();
        for(ReportImg reportImg:reportImgList){
            ReportImgDto reportImgDto = ReportImgDto.of(reportImg);
            reportImgDtoList.add(reportImgDto);
        }
        Report report_obj = reportRepository.findById(reportId).orElseThrow(EntityNotFoundException::new);
        ReportDto reportDto = ReportDto.of(report_obj);
        reportDto.setReportImgDtoList(reportImgDtoList);
        return reportDto;
    }

    public void del(Long reportId){
        reportImgRepository.deleteByReportId(reportId);
        reportRepository.deleteById(reportId);

    }

    public Long updateReport(ReportDto reportDto, List<MultipartFile> itemImgFileList)throws Exception{
        System.out.println(reportDto.getReportImgIds().get(0));
        Report report = reportRepository.findById(reportDto.getId()).orElseThrow(EntityNotFoundException::new);
        List<Long> itemImgIds = reportDto.getReportImgIds();
        for(int i=0; i<itemImgFileList.size(); i++){
            reportImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        report.updateItem(reportDto);
        return report.getId();
    }

}
