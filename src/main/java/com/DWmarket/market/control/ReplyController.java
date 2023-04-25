package com.DWmarket.market.control;

import com.DWmarket.market.entity.Reply;
import com.DWmarket.market.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    @Autowired
    private final ReplyService replyService;




    @PostMapping("/reply_write")
    public String replyWrite(@ModelAttribute Reply reply, @RequestParam("boardId") Long boardId, Principal principal){

        replyService.replyWrite(reply, principal.getName(), boardId);
        return "redirect:/board_write/"+boardId;

    }

    @GetMapping("/reply_delete")
    public String replyDelete(@RequestParam("replyId") Long replyId,@RequestParam("boardId") Long boardId){

        replyService.replyDelete(replyId);

        return "redirect:/board_write/"+boardId;
    }
}
