package com.DWmarket.market.service;

import com.DWmarket.market.Repository.MemberRepository;
import com.DWmarket.market.Repository.ReplyRepository;
import com.DWmarket.market.Repository.SalesBulletinBoardRepository;
import com.DWmarket.market.dto.ReplyDto;
import com.DWmarket.market.entity.Member;
import com.DWmarket.market.entity.Reply;
import com.DWmarket.market.entity.SalesBulletinBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    private final SalesBulletinBoardRepository boardRepository;

    private final MemberRepository userRepository;

    @Transactional(readOnly = true)
    public List<ReplyDto> getReplyView(Long id){
        List<Reply> reply = replyRepository.findByBoardId(id);  //게시물 번호 기준으로  댓글 조회 하기위해 boardId로 찾기
        //System.out.println( reply.get(0).getUser().getName());
        List<ReplyDto> replyDto = new ArrayList<>();  //해당 게시물의 댓글들을 모두 List로 가져왔고 view(html)에서 ReplyDto로 보이기를 했기때문에
        //ReplyDto타입의 LIST를 생성 한다.
        for( Reply temp : reply) {  //  댓글 을 LIST에 담은것을  다시 ReplyDto List로 담기위해  반복문 을 활용하여 옮기기
            replyDto.add(ReplyDto.of(temp)); //  ReplyDto  List에  담기
        }
        return replyDto;
    }

    public void replyWrite(Reply reply, String replyWriter, Long boardId){

        Member findUser = userRepository.findByEmail(replyWriter);
        Optional<SalesBulletinBoard> salesBulletinBoard = boardRepository.findById(boardId);

        reply.setBoard(salesBulletinBoard.get());
        reply.setUser(findUser);
        System.out.println(reply.getContent());
        replyRepository.save(reply);


    }

    public void replyDelete(Long replyId){
        Optional<Reply> reply = replyRepository.findById(replyId);
        replyRepository.delete(reply.get());
    }

}
