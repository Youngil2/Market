package com.DWmarket.market.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class adminController {

    @GetMapping(value = "/admin/myPage") // 마이페이지
    public String updateMember(){

        return "admin/adminPageForm";
    }
}
