package com.DWmarket.market.service;

import com.DWmarket.market.Repository.ReportImgRepository;

import com.DWmarket.market.entity.ReportImg;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportImgService {
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ReportImgRepository reportImgRepository;

    private final FileService fileService;

    public void saveItemImg(ReportImg reportImg, MultipartFile itemImgFile) throws  Exception{
        String oriImgName = itemImgFile.getOriginalFilename();// original 사진 이름값을 가져온다
        String imgName="";
        String imgUrl="";
        if( !StringUtils.isEmpty(oriImgName)){
            imgName=fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = "/images/item/"+imgName;
        }
        reportImg.updateItemImg(oriImgName, imgName, imgUrl);
        reportImgRepository.save(reportImg);
        }
    public void updateItemImg(Long reportImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){
            ReportImg savedItemImg = reportImgRepository.findById(reportImgId).orElseThrow(EntityExistsException::new);
            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation+"/"+ savedItemImg.getImgName());
            }
            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }

    }
}
