package com.project42.sai.notification;

import java.time.LocalDateTime;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project42.sai.dto.NotificationDto;
import com.project42.sai.dto.NotificationPayload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;
    private final SimpMessagingTemplate messagingTemplate;

    /*
    알림을 DB에 기록하고 동시에 인앱(webSocket) 알림을 전송
    *
     * @param userId   수신자 ID
     * @param type     알림 유형 (예: "CHAT")
     * @param title    알림 제목 (짧게)
     * @param message  알림 본문 (미리보기 등)
     * @param targetId 관련 엔티티 ID (채팅이라면 chatId)
     * @param linkUrl  알림 클릭 시 이동할 경로 (/chat?roomId=..)
     * 
     */
    @Transactional
    public void createAndPush(Long userId, String type, String title, String message, Long targetId, String linkUrl) {

        NotificationDto dto = new NotificationDto();
        dto.setUserId(userId);
        dto.setType(type);
        dto.setTitle(title);
        dto.setMessage(message);
        dto.setTargetId(targetId);
        dto.setIsRead(false);
        dto.setLinkUrl(linkUrl);
        // 1) DB 저장
        System.out.println("[NOTI] about to insert: userId=" + userId + ", title=" + title);
        notificationMapper.insert(dto);
        System.out.println("[NOTI] inserted. id=" + dto.getId());

         NotificationPayload payload = new NotificationPayload(
            type,
            title,
            message,
            linkUrl,
            LocalDateTime.now()
        );

        try {
            messagingTemplate.convertAndSendToUser(String.valueOf(userId), "/queue/notifications", payload);
        } catch (Exception e) {
            System.err.println("[NOTI] WS push failed: " + e.getMessage());
        }
        // 2) WebSocket 전송 (클라이언트가 '/user/queue/notifications' 구독해야 수신 가능)
       
        // convertAndSendToUser 의 첫 번째 인자는 Principal.getName() 값 → 보통 userId 문자열 사용
        
    }
}
