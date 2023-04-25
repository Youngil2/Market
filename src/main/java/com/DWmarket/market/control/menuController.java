package com.DWmarket.market.control;

import com.DWmarket.market.dto.ItemSearchDto;
import com.DWmarket.market.dto.MainItemDto;
import com.DWmarket.market.entity.SalesBulletinBoard;
import com.DWmarket.market.service.SalesBulletinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/hns")
@RequiredArgsConstructor
public class menuController {

    private final SalesBulletinBoardService sb;

    @GetMapping(value = {"/{category}","/{category}/{page}"})  //카테고리별 분류 법
    public String siho(@PathVariable("category")String category,Model model,
                       @PathVariable("page") Optional<Integer> page, ItemSearchDto itemSearchDto){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
        Page<MainItemDto> items =sb.categoryDiv(category,pageable,itemSearchDto);
//        items += sb.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items",items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("category",category);
        model.addAttribute("maxPage",5);
        return "menu/SportsMain";
    }

    @GetMapping("/chat")
    public String chat(){
        return "user/chat";
    }
}
