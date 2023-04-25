package com.DWmarket.market.Repository;

import com.DWmarket.market.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByBoardId(Long boardId);

    void deleteByBoardId(Long boardId);
}
