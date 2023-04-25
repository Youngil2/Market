package com.DWmarket.market.control;

import com.DWmarket.market.dto.ItemSearchDto;
import com.DWmarket.market.dto.MainItemDto;
import com.DWmarket.market.service.SalesBulletinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class maincontroller {

    private final SalesBulletinBoardService sr;

    @GetMapping("/")
    public String home(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model, HttpServletRequest request, Principal principal){
        HttpSession session = request.getSession();
        if(principal !=null){
            session.setAttribute("user",principal.getName());
        }


        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = sr.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items",items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "main";

    }
}
