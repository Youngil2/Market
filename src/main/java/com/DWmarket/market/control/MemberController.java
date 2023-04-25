package com.DWmarket.market.control;



import com.DWmarket.market.dto.ItemSearchDto;
import com.DWmarket.market.dto.MainItemDto;
import com.DWmarket.market.dto.MemberFormDto;
import com.DWmarket.market.dto.PassFormDto;
import com.DWmarket.market.entity.Member;
import com.DWmarket.market.service.MemberService;
import com.DWmarket.market.service.SalesBulletinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;


    private final SalesBulletinBoardService salesBulletinBoardService;


    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult br, Model model){
        if(br.hasErrors()){
            return "member/memberForm";
        }
        try{
            Member mem = Member.createMember(memberFormDto, passwordEncoder );
            memberService.saveMember(mem);
        }catch(Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";

    }
    @GetMapping("/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }


    @ModelAttribute("interest")
    public Map<String, String> interest(){
        Map<String, String> interest = new LinkedHashMap<>();
        interest.put("sports","스포츠");
        interest.put("furniture","가구");
        interest.put("baby","유아");
        interest.put("game","게임");
        interest.put("digital","디지털");
        interest.put("ticket","티켓/쿠폰");
        interest.put("pet","반려동물용품");
        interest.put("car","자동차용품");
        interest.put("free","무료나눔");

        return interest;
    }



    @PostMapping("/change/{memberId}")
    public String memberUpdate(@Valid MemberFormDto memberFormDto, BindingResult br,
                               Model model){
        if(br.hasErrors()){
            return "member/memberUpdateForm";
        }
        try{
            memberService.memberUpdate(memberFormDto,passwordEncoder);
        }catch (Exception e){
            model.addAttribute("errorMessage", "정보 수정 중 에러가 발생하였습니다.");
            return "member/memberUpdateForm";
        }
        return "redirect:/";
    }

    @GetMapping("/change") //정보수정
    public String UserName(Principal principal, Model model) {
        String email = principal.getName();
        MemberFormDto memberFormDto = memberService.getMemberInformation(email);
        model.addAttribute("memberFormDto",memberFormDto);
        return "member/memberUpdateForm";
    }

    @GetMapping("/find_id")   //  아이디 찾기(버튼부분)
    public String SearchId(){
        System.out.println("find_id");
        return "/member/Test1";
    }



    @PostMapping("/idupdate") // 아이디찾기(실행부분)
    public String idUpdate(@RequestParam String name, @RequestParam String tel, Model model){
        String email = memberService.searchId(name, tel);
        model.addAttribute("email",email);
        model.addAttribute("name", name);

        return "/member/idView";
    }

    @GetMapping(value = "/delete")
    public String delete(Principal principal){
        String email = principal.getName();
        memberService.delete(email);
        return "redirect:/members/logout";
    }






    @GetMapping("/pwupdate")
    public String pwup(){
        return "member/memberLoginForm";
    }

    @PostMapping("/pwupdate") //비밀번호 찾기
    public String pwUpdate(@RequestParam String name, @RequestParam String email, @RequestParam String sendEmail){

        memberService.searchPw(name, email, sendEmail);

        return "member/memberLoginForm";

    }
    @PostMapping("/pwchange") // 비밀번호 변경관련
    public String pwch(PassFormDto passFormDto, Model model, @RequestParam("email")String email){
        System.out.println("오늘옷이쁘다");
        memberService.passwordUpdate(passFormDto, email, passwordEncoder);


        model.addAttribute("passFormDto", passFormDto);

        //System.out.println("pwch 진입");
        return "redirect:/";
    }

    @GetMapping("/pwchange/{email}") // 비밀번호 변경관련
    public String pwch(Model model,@PathVariable("email")String email){
        //System.out.println("aaaaaaaaaaaa영준이는 방구쟁이");
        PassFormDto passFormDto = new PassFormDto();
        model.addAttribute("passFormDto",passFormDto);
        model.addAttribute("email",email);
        return "member/pwchange";
    }






    }




