package com.DWmarket.market.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccessDeniedController {
    @GetMapping("/accessDenied")
    public String getAccessDenied(Model model) {
        model.addAttribute("errorMessage","로그인 후 이용하세요");
        return "/member/memberLoginForm";
    }
}
