package com.project42.sai.chat;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project42.sai.dto.ChatDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatSqlMapper chatSqlMapper;

    // 채팅 저장
    public void saveMessage(ChatDto chatDto) {
        chatSqlMapper.insertChatMessage(chatDto);
    }

    // 채팅 출력
    public List<ChatDto> getChatHistory(Long coupleId) {
        List<ChatDto> list = chatSqlMapper.selectChatMessagesByCoupleId(coupleId);
        System.out.println("💬 불러온 채팅 수: " + list.size());
        return list;
    }

    // 읽음 표시
    public void markMessagesAsRead(Long coupleId, Long readerId) {
        chatSqlMapper.markMessagesAsRead(readerId, coupleId);
    }

}
