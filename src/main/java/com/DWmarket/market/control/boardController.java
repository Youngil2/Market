package com.DWmarket.market.control;

import com.DWmarket.market.Repository.LoginUser;

import com.DWmarket.market.dto.ItemSearchDto;
import com.DWmarket.market.dto.MemberFormDto;
import com.DWmarket.market.dto.ReplyDto;
import com.DWmarket.market.dto.SalesBulletinBoardFormDto;
import com.DWmarket.market.entity.SalesBulletinBoard;
import com.DWmarket.market.service.ReplyService;
import com.DWmarket.market.service.SalesBulletinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequiredArgsConstructor
public class boardController {
    private final SalesBulletinBoardService     sbbs;

    private final ReplyService replyService;




    @GetMapping("/board_write/{boardId}")
    public String itemDtl(Model model, @PathVariable("boardId") Long boardId, @LoginUser MemberFormDto user){
        SalesBulletinBoardFormDto salesBulletinBoardFormDto = sbbs.getItemDtl(boardId);

        List<ReplyDto> replyDto =replyService.getReplyView(boardId);

        model.addAttribute("replyDto", replyDto);
        model.addAttribute("salesBulletinBoardFormDto", salesBulletinBoardFormDto);
        return "board/SalesBulletinBoardDtl";
    }

    @GetMapping(value = "/user/board_write/{boardId}")
    public String itemDtl(@PathVariable("boardId") Long boardId, Model model){

        //System.out.println(boardId);
        try{
            SalesBulletinBoardFormDto salesBulletinBoardFormDto = sbbs.getItemDtl(boardId);
            model.addAttribute("salesBulletinBoardFormDto",salesBulletinBoardFormDto);
            //return "board/SalesBulletinBoardForm";
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "존재하지 않는 상품");
            model.addAttribute("salesBulletinBoardFormDto", new SalesBulletinBoardFormDto());
        }
        return "board/SalesBulletinBoardForm";
    }

    @PostMapping("/board_write/{boardId}")
    public String itemUpdate(@Valid SalesBulletinBoardFormDto salesBulletinBoardFormDto, BindingResult bindingResult, Model model , // @Valid - 유효성 검사 ItemFormDto 에 @NoBlank 검사를 위한
                             @RequestParam("itemImgFile")
                             List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()){// 상품등록시 필수값이 없다면
            return "board/SalesBulletinBoardForm";
        }

        System.out.println(salesBulletinBoardFormDto.getId());

        if(itemImgFileList.get(0).isEmpty() &&
                salesBulletinBoardFormDto.getId() == null){ // 상품 등록시 첫번째 이미지가 없다면
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력입니다.");
            return "board/SalesBulletinBoardForm";
        }
        try{
            sbbs.updateItem(salesBulletinBoardFormDto, itemImgFileList);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("수정 등록 실패");
            model.addAttribute("errorMessage", "수정중 에러가 발생했습니다.");
            return "board/SalesBulletinBoardForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/allProduct", "/allProduct/{page}"})
    public String sports(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 20);
        Page<SalesBulletinBoard> items = sbbs.getAdminItemPage(itemSearchDto,pageable);
        //System.out.println( items.getContent().get(0).getItemSellStatus()+" - menuController");
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);     //데이터는 모델로
        model.addAttribute("maxPage", 20);
//        System.out.println(items.getNumber()+"itemsnumber");
        return "board/SalesBulletinBoardMng";
    }





    @GetMapping("/user/board_write/new")
    public String salesBulletinBoardForm(Model model){
        model.addAttribute("salesBulletinBoardFormDto",new SalesBulletinBoardFormDto());

        return "board/SalesBulletinBoardForm";
    }

    @PostMapping("/board_write/new")
    public String BoardNew(@Valid SalesBulletinBoardFormDto sb,
                                        BindingResult bindingResult,
                                        @RequestParam("itemImgFile") List<MultipartFile> itemimgFileList, Model model){

        if(bindingResult.hasErrors()){
            System.out.println("에러");
            return "board/SalesBulletinBoardForm";

        }
        System.out.println(sb.getId());

        if(itemimgFileList.get(0).isEmpty() &&
                sb.getId() == null){ // 상품 등록시 첫번째 이미지가 없다면
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력입니다.");
            return "board/SalesBulletinBoardForm";
        }
        try{
            sbbs.saveItem(sb, itemimgFileList);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("상품 등록 실패");
            model.addAttribute("errorMessage", "상품등록중 에러가 발생했습니다.");
            return "board/SalesBulletinBoardForm";
        }
        return "redirect:/"; //상품등록이 완료 될시 첫 페이지로 이동
    }


    @GetMapping("/board_write/delete/{boardId}")
    public String boardDelete(@PathVariable("boardId") Long boardId, Long boardImgId ){

        sbbs.del(boardId);

        return "redirect:/allProduct";
    }

    @GetMapping("board_delete/{boardId}")
    public String deletes(@PathVariable("boardId") Long boardId){
        sbbs.del(boardId);
        return "redirect:/";
    }




}
