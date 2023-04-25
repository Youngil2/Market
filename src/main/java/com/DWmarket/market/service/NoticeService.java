package com.DWmarket.market.service;

import com.DWmarket.market.Repository.MemberRepository;
import com.DWmarket.market.Repository.NoticeRepository;
import com.DWmarket.market.constant.Role;
import com.DWmarket.market.dto.NoticeFormDto;
import com.DWmarket.market.dto.NoticeSearchDto;
import com.DWmarket.market.entity.Member;
import com.DWmarket.market.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;



    // 상세페이지
    @Transactional(readOnly = true)
    public NoticeFormDto getDetailPage(Long noticeId){
        Notice notice_obj = noticeRepository.findById(noticeId).orElseThrow(EntityNotFoundException::new);
        // orElseThrow(EntityNotFoundException::new); -> 존재하지 않을때 에러메시지를 띄우기 위함
        NoticeFormDto noticeFormDto = NoticeFormDto.of(notice_obj);




        return  noticeFormDto;

    }
    // 페이징 처리된 게시글을 리스트로 변환
    public Page<Notice> findNoticeList(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1, pageable.getPageSize());
        return noticeRepository.findAll(pageable);
    }

    // 게시글 ID로 저장
    public Notice findNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id).orElse(new Notice());
        return notice;
    }

    // 게시글 저장
    public Notice saveNotice(NoticeFormDto noticeFormDto) {
        Notice notice = new Notice();
        notice = notice.notice(noticeFormDto);

        noticeRepository.save(notice);

        return notice;
    }

    // 게시글 삭제
    public void delete(Long boardId){
        noticeRepository.deleteById(boardId);
    }

    public Object findNoticelist(Pageable pageable) {
        return null;
    }


    // 게시글 검색

    @Transactional(readOnly = true)
    public Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable){
        return noticeRepository.getAdminNoticePage( noticeSearchDto , pageable);
    }

    //게시글 수정

    @Transactional(readOnly = true)
    public NoticeFormDto getNoticeInfo(Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        NoticeFormDto noticeFormDto= NoticeFormDto.of(notice);
        return noticeFormDto;
    }

    public void noticeUpdate(Long noticeId, NoticeFormDto noticeFormDto){
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(EntityNotFoundException::new
        );
        notice.updateBoard(noticeFormDto);
    }


}
