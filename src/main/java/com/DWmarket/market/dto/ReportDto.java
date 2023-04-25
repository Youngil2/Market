package com.DWmarket.market.dto;

import com.DWmarket.market.entity.Report;
import com.DWmarket.market.entity.SalesBulletinBoard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReportDto {
    private Long id;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "상세설명은 필수 입력입니다.")
    private String detail;

    private String secret;     // 비밀글 여부

    private List<ReportImgDto> reportImgDtoList = new ArrayList<>();

    private String category;
    private String createBy;
    private List<Long> reportImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Report createItem(){
        Report report= new Report();
        return modelMapper.map(this,Report.class);
    }
    public static ReportDto of(Report report){
        return modelMapper.map(report, ReportDto.class);
    } // entit를 Dto로 바꿔주는 거 단 entity와 변수이름이 같아야 함.
}
