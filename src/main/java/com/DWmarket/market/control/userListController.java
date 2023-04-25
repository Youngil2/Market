package com.DWmarket.market.control;

import com.DWmarket.market.dto.MemberSearchDto;
import com.DWmarket.market.dto.NoticeSearchDto;
import com.DWmarket.market.entity.Member;
import com.DWmarket.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class userListController {
    private final MemberService ms;

    @GetMapping(value = {"/user/list", "/user/list/{page}"})
    public String user(@PathVariable("page") Optional<Integer> page, MemberSearchDto memberSearchDto, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 30);
        Page<Member> member = ms.getUserListPage(memberSearchDto, pageable);

        model.addAttribute("member", member);
        model.addAttribute("memberSearchDto", memberSearchDto);
        model.addAttribute("maxPage", 10);
        return "user/userList";
    }
}
