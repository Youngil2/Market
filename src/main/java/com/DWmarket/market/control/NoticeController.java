package com.DWmarket.market.control;

import com.DWmarket.market.dto.MemberFormDto;
import com.DWmarket.market.dto.NoticeFormDto;
import com.DWmarket.market.dto.NoticeSearchDto;
import com.DWmarket.market.entity.Notice;
import com.DWmarket.market.service.MemberService;
import com.DWmarket.market.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final MemberService memberService;

    @GetMapping(value = {"/notice/list", "/notice/list/{page}"})
    public String lists(@PathVariable("page") Optional<Integer> page,
                        NoticeSearchDto noticeSearchDto, Model model, Principal principal) {

        System.out.println(noticeSearchDto.getSearchQuery());


        String email = principal.getName();

        String name = memberService.ViewName(email);

        model.addAttribute("username", name);


        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10); //size 한페이지에 보여줄 상품수

        Page<Notice> notices = noticeService.getAdminNoticePage(noticeSearchDto, pageable);

        model.addAttribute("noticeSearchDto", noticeSearchDto);
        model.addAttribute("notices", notices);
        model.addAttribute("maxPage", 5);

        return "notice/list";
    }

    @GetMapping("/notice/write")
    public String newnewnew(Model model) {
        model.addAttribute("noticeFormDto", new NoticeFormDto());

        return "notice/write";
    }

    @PostMapping("/notice/write")
    public String noticeWrite(@Valid NoticeFormDto noticeFormDto, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "notice/write";
        }

        try {
            noticeService.saveNotice(noticeFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "공지사항 등록 중 에러가 발생하였습니다.");
            return "notice/write";
        }

        return "redirect:/notice/list";
    }

    // 상세페이지

    @GetMapping("/notice/detail/{noticeId}")
    public String noticeDetail(@PathVariable("noticeId") Long noticeId, Model model, Principal principal) {
        NoticeFormDto noticeFormDto = noticeService.getDetailPage(noticeId); // 서비스가서 만들기 여기부터
        model.addAttribute("noticeFormDto", noticeFormDto);

        String email = principal.getName();

        String name = memberService.ViewName(email);

        model.addAttribute("username", name);
        return "notice/detail";
    }


    //삭제

    @GetMapping(value = "/notice/delete/{noticeId}")
    public String del(@PathVariable("noticeId") Long noticeId, Model model){
        try {
            noticeService.delete(noticeId);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("공지사항 삭제 실패");
            model.addAttribute("errorMessage", "공지사항 삭제 중 에러가 발생하였습니다.");
            return "/notice/list";
        }

        return "redirect:/notice/list";
    }

    //공지수정
    @GetMapping("/notice/update/{noticeId}")
    public String updat(@PathVariable("noticeId") Long id, Model model) {

        NoticeFormDto noticeFormDto = noticeService.getNoticeInfo(id);
        model.addAttribute("noticeFormDto",noticeFormDto);
        return "/notice/write";
    }
    @PostMapping("/notice/update/{noticeId}")
    public String update(@PathVariable("noticeId") Long noticeId, @Valid NoticeFormDto noticeFormDto, BindingResult br, Model md){
        if(br.hasErrors()){
            return "/notice/write";
        }
        try {
            noticeService.noticeUpdate(noticeId, noticeFormDto);
        }catch (Exception e){
            e.printStackTrace();
            md.addAttribute("errorMessage","공지 수정 중 에러발생");
            return "/notice/write";
        }
        return "redirect:/notice/list";
    }



}
