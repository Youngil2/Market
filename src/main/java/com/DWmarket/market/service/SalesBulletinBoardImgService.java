package com.DWmarket.market.service;

import com.DWmarket.market.Repository.SalesBulletinBoardImgRepository;
import com.DWmarket.market.entity.SalesBulletinBoardImg;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesBulletinBoardImgService {

        @Value("${itemImgLocation}")
        private String itemImgLocation;

        private final SalesBulletinBoardImgRepository salesBulletinBoardImgRepository;

        private final FileService fileService;

    public void saveItemImg(SalesBulletinBoardImg salesBulletinBoardImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();// original 사진 이름값을 가져온다
        String imgName="";
        String imgUrl="";

        //파일 업로드
        if( !StringUtils.isEmpty(oriImgName)){ // original 이름이 있다면 참 없다면 false
            imgName=fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());  // entity ItemImg.java
            // 사용자가 상품의 이미지를 등록했다면 저장할 경로와 파일의 이름, 파일을 파일의 바이트 배열을 파일 업로드 파라미터로 uploadFile 메서드 호출
            // 호출 결과 실제 저장되는 파일의 이름을 imgName에 저장
            imgUrl = "/images/item/"+imgName;
            //저장한 상품 이미지를 불러올 경로를 설정
            // 실제 경로는 C:/toyImage/item 이지만 웹에서 브라우저로 접속을 하였을때 C:/toImage/item의 경로를 사용할 권한은 주어 지지 않는다.
            // 그래서 내부에서만 실제경로를 사용하고 외부에서는 접근권한이 있는 경로 로 사용하도록 리소스 연결 작업을 해주었다.
            // 즉 외부(브라우저를 통한 접속) 에서는 static하위 디렉토리인 images를 사용하도록 되어있다.
        }

        salesBulletinBoardImg.updateItemImg(oriImgName, imgName, imgUrl);
        salesBulletinBoardImgRepository.save(salesBulletinBoardImg); //테이블 저장
    }

    public void updateItemImg(Long boardImgId, MultipartFile itemImgFile) throws Exception{
                if(!itemImgFile.isEmpty()){
                    SalesBulletinBoardImg savedItemImg = salesBulletinBoardImgRepository.findById(boardImgId).orElseThrow(EntityNotFoundException::new);

                    //기존 이미지 파일 삭제
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
