package com.project42.sai.chat;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.project42.sai.couple.CoupleService;
import com.project42.sai.dto.ChatDto;
import com.project42.sai.dto.CoupleDto;
import com.project42.sai.notification.NotificationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final CoupleService coupleService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatDto chatDto) {

        chatDto.setSentAt(LocalDateTime.now());
        chatDto.setIsRead(false);
        // 채팅 저장
        chatService.saveMessage(chatDto);
        // ✅ 동적 구독 경로로 직접 전송

        String dest = "/topic/room/" + chatDto.getCoupleId();
        messagingTemplate.convertAndSend(dest, chatDto);

        // 3) 수신자 판별
        CoupleDto couple = coupleService.getCoupleById(chatDto.getCoupleId());
        if (couple != null && chatDto.getSenderUserId() != null) {
            Long senderId = chatDto.getSenderUserId();
            Long receiverId = couple.getFemaleUserId().equals(senderId)
                    ? couple.getMaleUserId()
                    : couple.getFemaleUserId();

            //  4) 알림 미리 보기
            String preview;
            if ("IMAGE".equals(chatDto.getMessageType())) {
                preview = "사진을 보냈습니다.";
            } else {
                String content = chatDto.getContent() == null ? "" : chatDto.getContent();
                preview = content.length() > 20 ? content.substring(0, 20) + "..." : content;
            }

            String title = (chatDto.getSenderNickname() != null ? chatDto.getSenderNickname() : "상대")
                    + "님의 메시지";

            try {
                notificationService.createAndPush(
                        receiverId,
                        "CHAT",
                        title,
                        preview,
                        chatDto.getCoupleId(),
                        "/chat/room/" + chatDto.getCoupleId()
                );
                System.out.println("[NOTI] queued → receiverId=" + receiverId);
            } catch (Exception e) {
                System.err.println("[NOTI] createAndPush failed: " + e.getMessage());
            }

        }
    }
    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleWsError(Exception e) {
        return e.getMessage();
    }
}
