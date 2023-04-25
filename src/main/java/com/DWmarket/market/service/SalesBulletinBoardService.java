package com.DWmarket.market.service;

import com.DWmarket.market.Repository.ReplyRepository;
import com.DWmarket.market.Repository.SalesBulletinBoardImgRepository;
import com.DWmarket.market.Repository.SalesBulletinBoardRepository;
import com.DWmarket.market.dto.ItemSearchDto;
import com.DWmarket.market.dto.MainItemDto;
import com.DWmarket.market.dto.SalesBulletinBoardFormDto;
import com.DWmarket.market.dto.SalesBulletinBoardImgDto;
import com.DWmarket.market.entity.SalesBulletinBoard;
import com.DWmarket.market.entity.SalesBulletinBoardImg;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SalesBulletinBoardService {

    private final SalesBulletinBoardRepository sr;

    private final SalesBulletinBoardImgService salesBulletinBoardImgService;

    private final SalesBulletinBoardImgRepository sir;

    private final ReplyRepository rp;



    public Long updateItem(SalesBulletinBoardFormDto salesBulletinBoardFormDto, List<MultipartFile> itemImgFileList) throws Exception{ // ItemController에 수정중 실패로

        SalesBulletinBoard salesBulletinBoard = sr.findById(salesBulletinBoardFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        List<Long> itemImgIds = salesBulletinBoardFormDto.getItemImgIds();
        //이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++){
            salesBulletinBoardImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i) );
        }
        salesBulletinBoard.updateItem(salesBulletinBoardFormDto);
        return salesBulletinBoard.getId();
    }

    public Long saveItem(SalesBulletinBoardFormDto sb,
                         List<MultipartFile> itemImgFileList) throws Exception {
        //상품 등록
        SalesBulletinBoard item_obj = sb.createItem();
        System.out.println( item_obj.getRegTime());
        sr.save(item_obj);

        //이미지 등록
        for( int i=0; i<itemImgFileList.size(); i++  ){
            SalesBulletinBoardImg salesBulletinBoardImg = new SalesBulletinBoardImg();
            salesBulletinBoardImg.setBoard(item_obj);
            if( i==0 ) // 첫번째 등록한 이미지를 대표 이미지로 사용
                salesBulletinBoardImg.setMainImgYn("Y"); //대표이미지는 Y
            else
                salesBulletinBoardImg.setMainImgYn("N");

            salesBulletinBoardImgService.saveItemImg(salesBulletinBoardImg, itemImgFileList.get(i) );
        }

        return item_obj.getId();
    }

    @Transactional(readOnly = true)
    public SalesBulletinBoardFormDto getItemDtl(Long boardId){  // 조회한 결과는 entity에 있으니 entity로 검색 해서 있다면 Dto로 바꿔준다.

        List<SalesBulletinBoardImg> salesBulletinBoardImgList = sir.findByBoardIdOrderByIdAsc(boardId);   // item.java 에 상품에 이미 정해놓은 name=item_id

        System.out.println(salesBulletinBoardImgList.size());

        List<SalesBulletinBoardImgDto> salesBulletinBoardImgDtoList = new ArrayList<>();                //Id기준으로 오름차순(Asc)으로 정렬하겠다.
        for(SalesBulletinBoardImg salesBulletinBoardImg : salesBulletinBoardImgList){     // itemImg(entity)에 있는 객체를 Dto로 바꿔주는 것  {ItemImgRepository에서 가져온거}
            SalesBulletinBoardImgDto salesBulletinBoardImgDto = SalesBulletinBoardImgDto.of(salesBulletinBoardImg);
            salesBulletinBoardImgDtoList.add(salesBulletinBoardImgDto);
        }


        SalesBulletinBoard item_obj = sr.findById(boardId).orElseThrow(EntityNotFoundException::new);
        // (orElseThrow) findById로 조회하는데 없을경우 에러가 강재로발생하도록 있는것 이유는 ItemController에서 존재하지 않는 상품
        SalesBulletinBoardFormDto salesBulletinBoardFormDto = SalesBulletinBoardFormDto.of(item_obj);//entity를 Dto객체로 바꿔주는 작업
        salesBulletinBoardFormDto.setSalesBulletinBoardImgDtoList(salesBulletinBoardImgDtoList);//entity를 Dto객체로 바꿔주는 작업
        return salesBulletinBoardFormDto;
    }

    @Transactional(readOnly = true) //데이터베이스에서 수정, 삭제, 추가, 조회 할경우에 오류발생시 오류발생전으로 돌릴수있다. //readOnly는 조회할때 쓰이는데 따로 불필요한 권한을 주지 않기위함에 성능이나 파일이 꺠질우려가 있기에
    public Page<SalesBulletinBoard> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){

        return sr.getAdminItemPage(itemSearchDto,pageable); // ItemRepositoryCustomlmpl에 있다.

    }

    @Transactional(readOnly = true)
    public  Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return  sr.getMainItemPage(itemSearchDto,pageable);
    }


    public void del(Long boardId){
        rp.deleteByBoardId(boardId);
        sir.deleteByBoardId(boardId);
        sr.deleteById(boardId);
    }

    public Page<MainItemDto> categoryDiv(String category, Pageable pageable,ItemSearchDto itemSearchDto) {
        return sr.findByCategoryDiv(category,pageable,itemSearchDto);
    }


}
