package com.project42.sai.chat;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.project42.sai.dto.ChatDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
   private final ChatService chatService;
   private final SimpMessagingTemplate messagingTemplate; 
   
      @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatDto chatDto) {

        chatDto.setSentAt(LocalDateTime.now());
        chatDto.setIsRead(false);

        chatService.saveMessage(chatDto);

        // ✅ 동적 구독 경로로 직접 전송
        messagingTemplate.convertAndSend("/topic/room/" + chatDto.getCoupleId(), chatDto);
    }


}
