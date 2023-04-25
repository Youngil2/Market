package com.DWmarket.market.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] fileDate) throws Exception{
        UUID uuid = UUID.randomUUID(); // 랜덤 이미지 이름생성중복이 이름방지 위해
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String  savedFileName = uuid.toString()+extension;//uuid와 원래 이름 조합해서 저장할 이름
        String fileUploadFullurl = uploadPath+"/"+savedFileName; // 경로와 파일이름을 설정한값
        FileOutputStream fos = new FileOutputStream(fileUploadFullurl); //파일 저장객체
        fos.write(fileDate);// 파일 쓰기 (실제 데이터는 여기)
        fos.close();
        return savedFileName; //업로드된 파일 이름 반환
    }
    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);// 파일이 저장된 경로로 파일 객체 생성
        if(deleteFile.exists()){ // 파일이 존재하면 파일 삭제
            deleteFile.delete();
            log.info("파일을 삭제 하였습니다");
        }else{
            log.info("파일이 존재 하지 않습니다.");
        }
    }
}
