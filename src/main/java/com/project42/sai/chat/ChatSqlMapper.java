package com.project42.sai.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.project42.sai.dto.ChatDto;

@Mapper
public interface ChatSqlMapper {
    // 채팅 저장
    void insertChatMessage(ChatDto chatDto);
    // 채팅 내역 출력
    List<ChatDto> selectChatMessagesByCoupleId(@Param("coupleId") Long coupleId);
    // 읽음 여부
    void markMessagesAsRead (@Param("readerId") Long readerId , @Param("coupleId") Long coupleId);
}
