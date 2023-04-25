package com.DWmarket.market.control;

import com.DWmarket.market.Repository.LoginUser;
import com.DWmarket.market.dto.*;
import com.DWmarket.market.entity.Notice;
import com.DWmarket.market.entity.Report;
import com.DWmarket.market.entity.SalesBulletinBoard;
import com.DWmarket.market.service.NoticeService;
import com.DWmarket.market.service.ReportService;
import com.DWmarket.market.service.SalesBulletinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class reportController {


    private final ReportService reportService;

    @GetMapping(value = {"/Mng","/Mng/{Page}"})
    public String Mng(@PathVariable("page") Optional<Integer> page, ReportSearchDto reportSearchDto, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 20);
        System.out.println(reportSearchDto.getSearchQuery()+"reportController - Mng");

        Page<Report> items = reportService.getAdminPage(reportSearchDto,pageable);
        model.addAttribute("items", items);
        model.addAttribute("reportSearchDto", reportSearchDto);     //데이터는 모델로
        model.addAttribute("maxPage", 20);
        return "report/reportBoardMng";
    }

    @GetMapping("/write/new")
    public String report(Model model){
        model.addAttribute("reportDto",new ReportDto());
        return "report/reportForm";
    }
    @PostMapping("/write/new")
    public String reportNew(@Valid ReportDto reportDto, BindingResult bindingResult, @RequestParam("itemImgFile")List<MultipartFile> itemimgFileList, Model model
                            ){
        if (bindingResult.hasErrors()) {
            System.out.println("에러");
            return "report/reportForm";
        }
        if(itemimgFileList.get(0).isEmpty() &&
                reportDto.getId() == null){ // 상품 등록시 첫번째 이미지가 없다면
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력입니다.");
            return "report/reportForm";
        }
        try {
            reportService.saveReport(reportDto, itemimgFileList);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","문의 및 신고 게시판 등록중 에러 발생");
            return "report/reportForm";
        }
        return "redirect:/report/Mng";

    }
    @GetMapping("/detail/{reportId}")
    public String getItemDtl(Model model, @PathVariable("reportId") Long boardId){
        ReportDto reportDto = reportService.getItemDtl(boardId);



        model.addAttribute("reportDto", reportDto);
        return "report/reportBoardDtl";
    }

    @GetMapping("/write/delete/{reportId}")
    public String dell(@PathVariable("reportId") Long reportId){
        reportService.del(reportId);
        return "redirect:/report/Mng";
    }
    @GetMapping("/user/write/new/{reportId}")
    public String itemDtl(Model model, @PathVariable("reportId") Long reportId){
        try {
            ReportDto reportDto=reportService.getItemDtl(reportId);
            model.addAttribute("reportDto",reportDto);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","존재하지 않는 문의사항입니다.");
            model.addAttribute("reportDto",new ReportDto());

        }
        return "report/reportForm";


    }
    @PostMapping("/write/new/{reportId}")
    public String reportUpdate(@Valid ReportDto reportDto,BindingResult bindingResult,
                               Model model,@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){
        if(bindingResult.hasErrors()){
            return "report/reportForm";
        }
        if(itemImgFileList.get(0).isEmpty() &&
                reportDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력입니다.");
            return "report/reportForm";
        }
        try {
            System.out.println(reportDto.getId() +"   itemImgFileList");
            reportService.updateReport(reportDto, itemImgFileList);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "수정중 에러가 발생했습니다.");
            return "report/reportForm";
        }
        System.out.println(reportDto.getSecret()+"시크릿");
        return "redirect:/report/Mng";
    }

}
